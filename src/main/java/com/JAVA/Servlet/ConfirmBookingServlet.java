package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.*;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.*; 
import com.JAVA.Beans.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/driver/bookings/confirm")
public class ConfirmBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            int bookingId = Integer.parseInt(request.getParameter("id"));
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            
            BookingBean booking = bookingDAO.findById(bookingId);
            if (booking == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
                return;
            }
            
            TripDAO tripDAO = new TripDAOImpl(conn);
            TripBean trip = tripDAO.findById(tripId);
            if (trip == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trip not found");
                return;
            }
            
            if (bookingDAO.updateStatus(bookingId, "CONFIRMED")) {
                
                PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
                int passengerUserId = passengerDAO.findUserIdByPassengerId(booking.getPassengerId());
                
                NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                NotificationBean notification = new NotificationBean();
                notification.setUserId(passengerUserId);
                notification.setType("BOOKING_CONFIRMED");
                notification.setMessage("Your booking for " + trip.getDepartureCity() + " → " +
                        trip.getArrivalCity() + " on " + trip.getDateTime().toLocalDate() + " is confirmed.");
          
                String link = request.getContextPath() + "/passenger/trips/details?id=" + tripId;
                notification.setMetadata("{\"url\": \"" + link + "\"}");
                notifDAO.create(notification);
                
                response.sendRedirect(request.getContextPath() + "/driver/trips/details?id=" + tripId + "&bookingUpdated=1");
                
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to confirm booking");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error confirming booking: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}