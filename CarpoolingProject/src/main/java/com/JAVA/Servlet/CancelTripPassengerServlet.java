package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.*;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.*; 
import com.JAVA.Beans.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/passenger/bookings/cancel")
public class CancelTripPassengerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int bookingId = Integer.parseInt(request.getParameter("id"));
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            
            if (bookingDAO.updateStatus(bookingId, "CANCELLED")) {
                BookingBean booking = bookingDAO.findById(bookingId);
                
                TripDAO tripDAO = new TripDAOImpl(conn);
                TripBean trip = tripDAO.findById(booking.getTripId());

                DriverDAO driverDAO = new DriverDAOImpl(conn);
                int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());

                NotificationBean notification = new NotificationBean();
                notification.setUserId(driverUserId);
                notification.setType("BOOKING_CANCELLED_BY_PASSENGER");
                notification.setMessage(
                    "A passenger cancelled a booking for " +
                    trip.getDepartureCity() + " → " + 
                    trip.getArrivalCity() + " on " +
                    trip.getDateTime().toLocalDate() + "."
                );
                String link = request.getContextPath() + "/driver/trips/details?id=" + trip.getId();
                notification.setMetadata("{\"url\":\"" + link + "\"}");

                NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                notifDAO.create(notification);

                response.sendRedirect(request.getContextPath() + "/passenger/dashboard?bookingCancelled=1");
            } else {
                throw new Exception("Failed to cancel booking");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/passenger/dashboard").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}