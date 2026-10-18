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
import java.util.List;

@WebServlet("/driver/trips/complete")
public class CompleteTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int tripId = Integer.parseInt(request.getParameter("id"));
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            TripDAO tripDAO = new TripDAOImpl(conn);
            TripBean trip = tripDAO.findById(tripId);
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            bookingDAO.markTripAsCompleted(tripId);
            
            List<BookingBean> bookings = bookingDAO.findByTripId(tripId);

            for (BookingBean booking : bookings) {
                if ("COMPLETED".equals(booking.getStatus())) {
                    PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
                    int passengerUserId = passengerDAO.findUserIdByPassengerId(booking.getPassengerId());
                    
                    DriverDAO driverDAO = new DriverDAOImpl(conn);
                    int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());

                    NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                    NotificationBean notification = new NotificationBean();
                    notification.setUserId(passengerUserId);
                    notification.setType("TRIP_COMPLETED");

                    notification.setMessage(
                        "Trip completed. Please rate your driver for " +
                        trip.getDepartureCity() + " → " + trip.getArrivalCity() +
                        " on " + trip.getDateTime().toLocalDate() + "."
                    );

                    String rateUrl = request.getContextPath()
                        + "/feedback?tripId=" + tripId
                        + "&targetId=" + driverUserId
                        + "&type=DRIVER";

                    notification.setMetadata("{\"url\":\"" + rateUrl + "\",\"cta\":\"Rate driver\",\"tripId\":" + tripId + ",\"driverUserId\":" + driverUserId + "}");
                    notifDAO.create(notification);
                }
            }
            
            response.sendRedirect(request.getContextPath() + "/driver/trips/details?id=" + tripId + "&tripCompleted=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error completing trip: " + e.getMessage());
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