package com.JAVA.Servlet;

import com.JAVA.Beans.DriverBean;
import com.JAVA.Beans.PassengerBean;
import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.Exception.DAOException;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Implement.DriverDAOImpl;
import com.JAVA.DAO.Implement.PassengerDAOImpl;
import com.JAVA.DAO.Implement.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (!"edit".equals(action)) {
            resp.sendError(404);
            return;
        }
        int userId = Integer.parseInt(req.getParameter("id"));

        Connection conn = null;
        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();
            UserDAOImpl userDAO = new UserDAOImpl(conn);
            DriverDAOImpl driverDAO = new DriverDAOImpl(conn);

            UserBean user = userDAO.findById(userId);
            if (user == null) {
                resp.sendRedirect(req.getContextPath()+"/views/error.jsp?msg=User not found");
                return;
            }

            String licenceNumber = null;
            if ("DRIVER".equals(user.getRole())) {
                int driverId = driverDAO.getDriverIdByUserId(userId);
                if (driverId > 0) {
                    var d = driverDAO.findById(driverId);
                    if (d != null) licenceNumber = d.getLicenceNumber();
                }
            }

            req.setAttribute("user", user);
            req.setAttribute("licenceNumber", licenceNumber); 
            req.getRequestDispatcher("/views/admin/editUser.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath()+"/views/error.jsp?msg=Failed to load user");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        Connection conn = null;

        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();
            conn.setAutoCommit(false);

            UserDAOImpl userDAO = new UserDAOImpl(conn);
            DriverDAOImpl driverDAO = new DriverDAOImpl(conn);
            PassengerDAOImpl passengerDAO = new PassengerDAOImpl(conn);

            if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String email = req.getParameter("email");
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String phone = req.getParameter("phoneNumber");
                String roleParam = req.getParameter("role");         
                String licence = req.getParameter("licenceNumber");  

                UserBean u = userDAO.findById(id);
                if (u == null) throw new DAOException(new IllegalArgumentException("User not found"));

                final String originalRole = u.getRole();
                final String newRole = (roleParam == null || roleParam.isBlank()) ? originalRole : roleParam;
                final boolean roleChanged = !originalRole.equals(newRole);

                u.setEmail(email);
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setPhoneNumber(phone);
                u.setRole(newRole);
                if (!userDAO.update(u)) throw new DAOException(new RuntimeException("User update failed"));

                if (roleChanged) {
                    switch (newRole) {
                        case "DRIVER": {
                            if (licence == null || licence.isBlank()) {
                                throw new DAOException(new IllegalArgumentException("Licence is required when switching to DRIVER"));
                            }
                            int pid = passengerDAO.findByUserId(id);
                            if (pid > 0) passengerDAO.deleteByUserId(id);

                            DriverBean d = new DriverBean();
                            d.setUserId(id);
                            d.setLicenceNumber(licence);
                            driverDAO.create(d);
                            
                            break;
                        }
                        case "PASSENGER": {
                            int did = driverDAO.getDriverIdByUserId(id);
                            if (did > 0) driverDAO.deleteByUserId(id);

                            PassengerBean p = new PassengerBean();
                            p.setUserId(id);
                            passengerDAO.create(p);
                            break;
                        }
                        case "ADMIN": {
                        	if (originalRole.equals("DRIVER")) {
	                            int did = driverDAO.getDriverIdByUserId(id);
	                            if (did > 0) driverDAO.deleteByUserId(id);
                        	} else {
	                            int pid = passengerDAO.findByUserId(id);
	                            if (pid > 0) passengerDAO.deleteByUserId(id);
                        	}
                            break;
                        }
                        default:
                            throw new DAOException(new IllegalArgumentException("Unsupported role: " + newRole));
                    }
                } else {
                    switch (newRole) {
                        case "DRIVER": {
                            int did = driverDAO.getDriverIdByUserId(id);
                            if (did <= 0) {
                                if (licence != null && !licence.isBlank()) {
                                    DriverBean d = new DriverBean();
                                    d.setUserId(id);
                                    d.setLicenceNumber(licence);
                                    driverDAO.create(d);
                                }
                            } else {
                                if (licence != null && !licence.isBlank()) {
                                    DriverBean d = new DriverBean();
                                    d.setId(did);
                                    d.setUserId(id);
                                    d.setLicenceNumber(licence);
                                    driverDAO.update(d);
                                }
                            }
                            break;
                        }
                        case "PASSENGER": {
                            int pid = passengerDAO.findByUserId(id);
                            if (pid <= 0) {
                                PassengerBean p = new PassengerBean();
                                p.setUserId(id);
                                passengerDAO.create(p);
                            }
                            break;
                        }
                        case "ADMIN":
                            break;
                        default:
                            throw new DAOException(new IllegalArgumentException("Unsupported role: " + newRole));
                    }
                }

                conn.commit();
                resp.sendRedirect(req.getContextPath()+"/admin/dashboard?"+id+"&success=Saved");
                return;
            }


            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean ok = userDAO.deleteById(id);
                if (!ok) throw new DAOException(new RuntimeException("Delete failed"));

                conn.commit();
                resp.sendRedirect(req.getContextPath()+"/admin/dashboard?success=User%20deleted");
                return;
            }

            conn.rollback();
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard?error=Unknown%20action");

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard?error=Operation%20failed");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }

}