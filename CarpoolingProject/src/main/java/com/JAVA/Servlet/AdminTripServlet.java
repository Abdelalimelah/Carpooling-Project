package com.JAVA.Servlet;

import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Exception.DAOException;
import com.JAVA.DAO.Implement.BookingDAOImpl;
import com.JAVA.DAO.Implement.TripDAOImpl;
import com.JAVA.DAO.Implement.DriverDAOImpl;
import com.JAVA.DAO.Implement.UserDAOImpl;
import com.JAVA.DAO.Implement.CarDAOImpl;
import com.JAVA.Beans.BookingBean;
import com.JAVA.Beans.TripBean;
import com.JAVA.Beans.UserBean;
import com.JAVA.Beans.CarBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/admin/trips")
public class AdminTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String ret = req.getContextPath() + "/admin/dashboard";

        if (!"view".equals(action) && !"edit".equals(action)) {
            resp.sendRedirect(ret + "?error=Unknown%20action");
            return;
        }

        Connection conn = null;
        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();

            TripDAOImpl tripDAO = new TripDAOImpl(conn);
            DriverDAOImpl driverDAO = new DriverDAOImpl(conn);
            UserDAOImpl userDAO = new UserDAOImpl(conn);
            CarDAOImpl carDAO = new CarDAOImpl(conn);
            BookingDAOImpl bookingDAO = new BookingDAOImpl(conn);

            int id = Integer.parseInt(req.getParameter("id"));

            TripBean trip = tripDAO.findById(id);
            if (trip == null) {
                resp.sendRedirect(ret + "?error=Trip%20not%20found");
                return;
            }

            // Optional: resolve driver user + car to display in view/edit pages
            int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());
            UserBean driverUser = (driverUserId > 0) ? userDAO.findById(driverUserId) : null;
            CarBean car = carDAO.findById(trip.getCarId());
            List<BookingBean> bookings = bookingDAO.findByTripId(id);

            req.setAttribute("trip", trip);
            req.setAttribute("driverUser", driverUser);
            req.setAttribute("car", car);
            req.setAttribute("bookings", bookings);
            req.setAttribute("ret", ret);

            if ("view".equals(action)) {
                req.getRequestDispatcher("/views/admin/trips/view.jsp").forward(req, resp);
            } else { // edit
                req.getRequestDispatcher("/views/admin/trips/edit.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(ret + "?error=Failed%20to%20load%20trip");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String ret = req.getContextPath() + "/admin/dashboard";

        Connection conn = null;
        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();
            conn.setAutoCommit(false);

            TripDAOImpl tripDAO = new TripDAOImpl(conn);
            BookingDAOImpl bookingDAO = new BookingDAOImpl(conn);

            int id = Integer.parseInt(req.getParameter("id"));

            switch (action == null ? "" : action) {
                case "delete": {
                    boolean ok = tripDAO.delete(id);
                    if (!ok) throw new DAOException(new RuntimeException("Delete failed"));
                    conn.commit();
                    resp.sendRedirect(ret + "?success=Trip%20deleted");
                    return;
                }
                case "forceComplete": {
                    // If already completed, do nothing
                    if (bookingDAO.tripIsCompleted(id)) {
                        conn.rollback();
                        resp.sendRedirect(ret + "?info=Trip%20already%20completed");
                        return;
                    }
                    List<BookingBean> bookings = bookingDAO.findByTripId(id);
                    if (bookings == null || bookings.isEmpty()) {
                        conn.rollback();
                        resp.sendRedirect(ret + "?error=Cannot%20complete%20a%20trip%20with%20no%20bookings");
                        return;
                    }
                    bookingDAO.markTripAsCompleted(id);
                    if (!bookingDAO.tripIsCompleted(id)) {
                        conn.rollback();
                        resp.sendRedirect(ret + "?error=No%20eligible%20bookings%20to%20mark%20completed");
                        return;
                    }
                    conn.commit();
                    resp.sendRedirect(ret + "?success=Trip%20forced%20to%20COMPLETED");
                    return;
                }
                case "update": {
                    // Update basic fields (keep driver/car as-is unless you want to allow changes)
                    TripBean t = tripDAO.findById(id);
                    if (t == null) throw new DAOException(new IllegalArgumentException("Trip not found"));

                    String departureCity = req.getParameter("departureCity");
                    String arrivalCity   = req.getParameter("arrivalCity");
                    String dateTimeStr   = req.getParameter("dateTime"); // "yyyy-MM-dd'T'HH:mm"
                    String priceStr      = req.getParameter("price");
                    String seatsStr      = req.getParameter("seatsAvailable");
                    String carIdStr      = req.getParameter("carId"); // optional

                    if (departureCity != null) t.setDepartureCity(departureCity.trim());
                    if (arrivalCity   != null) t.setArrivalCity(arrivalCity.trim());
                    if (dateTimeStr   != null && !dateTimeStr.isBlank()) {
                        LocalDateTime dt = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                        t.setDateTime(dt);
                    }
                    if (priceStr != null && !priceStr.isBlank()) {
                        t.setPrice(new java.math.BigDecimal(priceStr));
                    }
                    if (seatsStr != null && !seatsStr.isBlank()) {
                        t.setSeatsAvailable(Byte.parseByte(seatsStr));
                    }
                    if (carIdStr != null && !carIdStr.isBlank()) {
                        t.setCarId(Integer.parseInt(carIdStr));
                    }

                    boolean ok = tripDAO.update(t);
                    if (!ok) throw new DAOException(new RuntimeException("Update failed"));

                    conn.commit();
                    resp.sendRedirect(ret + "?success=Trip%20updated");
                    return;
                }
                default: {
                    conn.rollback();
                    resp.sendRedirect(ret + "?error=Unknown%20action");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
            resp.sendRedirect((ret != null ? ret : (req.getContextPath()+"/admin/dashboard"))
                    + "&error=Operation%20failed");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }
    
}