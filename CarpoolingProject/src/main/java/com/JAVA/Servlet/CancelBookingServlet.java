package com.JAVA.Servlet;

import java.io.IOException; 
import java.sql.Connection;

import com.JAVA.DAO.Implement.*;
import com.JAVA.DAO.*;
import com.JAVA.Beans.*;
import com.JAVA.DAO.Factory.DAOFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/driver/bookings/cancel")
public class CancelBookingServlet extends HttpServlet {
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
            
            if (bookingDAO.updateStatus(bookingId, "CANCELLED")) {
                response.sendRedirect(request.getContextPath() + "/driver/trips/details?id=" + tripId + "&bookingUpdated=1");
                
                PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
                int passengerUserId = passengerDAO.findUserIdByPassengerId(booking.getPassengerId());

                NotificationBean notification = new NotificationBean();
                notification.setUserId(passengerUserId);
                notification.setType("BOOKING_CANCELLED");
                notification.setMessage("Your booking for " + trip.getDepartureCity() + " → " + 
                                      trip.getArrivalCity() + " was cancelled by the driver.");
                NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                notifDAO.create(notification);
                
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to cancel");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
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