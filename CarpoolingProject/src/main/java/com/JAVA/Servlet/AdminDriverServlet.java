package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.AdminDAOImpl;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.Beans.DriverBean;
import com.JAVA.Beans.UserBean;
import com.JAVA.Utils.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/drivers")
public class AdminDriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        Connection conn = null;

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            AdminDAOImpl adminDAO = new AdminDAOImpl(conn);

            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                DriverBean driver = adminDAO.getDriverById(id);
                request.setAttribute("driver", driver);
                request.getRequestDispatcher("/views/admin/editDriver.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                adminDAO.deleteDriver(id);
                response.sendRedirect(request.getContextPath() + "/admin/drivers?success=Driver deleted successfully");
            } else {
                // List all drivers
                request.setAttribute("drivers", adminDAO.getAllDrivers());
                request.getRequestDispatcher("/views/admin/drivers.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/drivers?error=Operation failed");
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        Connection conn = null;

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            AdminDAOImpl adminDAO = new AdminDAOImpl(conn);

            if ("add".equals(action)) {
                // Create user first
                UserBean user = new UserBean();
                user.setEmail(request.getParameter("email"));
                user.setPasswordHash(PasswordUtil.hashPassword(request.getParameter("password")));
                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setPhoneNumber(request.getParameter("phoneNumber"));

                // Create driver
                DriverBean driver = new DriverBean();
                driver.setLicenceNumber(request.getParameter("licenceNumber"));

                if (adminDAO.addDriver(driver, user)) {
                    response.sendRedirect(request.getContextPath() + "/admin/drivers?success=Driver added successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/drivers?error=Failed to add driver");
                }

            } else if ("update".equals(action)) {
                DriverBean driver = new DriverBean();
                driver.setId(Integer.parseInt(request.getParameter("id")));
                driver.setUserId(Integer.parseInt(request.getParameter("userId")));
                driver.setLicenceNumber(request.getParameter("licenceNumber"));

                UserBean user = new UserBean();
                user.setEmail(request.getParameter("email"));
                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setPhoneNumber(request.getParameter("phoneNumber"));

                if (adminDAO.updateDriver(driver, user)) {
                    response.sendRedirect(request.getContextPath() + "/admin/drivers?success=Driver updated successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/drivers?error=Failed to update driver");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/drivers?error=Operation failed");
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
}