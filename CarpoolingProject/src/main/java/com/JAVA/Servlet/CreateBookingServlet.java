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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/passenger/bookings/create")
public class CreateBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
            int passengerId = passengerDAO.findByUserId(user.getId());
            
            TripDAO tripDAO = new TripDAOImpl(conn);
            TripBean trip = tripDAO.findById(tripId);
            if (trip == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trip not found");
                return;
            }
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            int confirmedBookings = bookingDAO.countConfirmedBookingsByTripId(tripId);
            int seatsLeft = trip.getSeatsAvailable() - confirmedBookings;
            
            if (seatsLeft <= 0) {
                request.setAttribute("errorMessage", "No seats available for this trip.");
                request.getRequestDispatcher("/passenger/trips").forward(request, response);
                return;
            }
            
            BookingBean booking = new BookingBean();
            booking.setTripId(tripId);
            booking.setPassengerId(passengerId);
            booking.setStatus("PENDING"); 
            
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());

            NotificationBean notification = new NotificationBean();
            notification.setUserId(driverUserId);
            notification.setType("NEW_BOOKING");
            notification.setMessage("New booking request from " + user.getFirstName() + " for " +
                         trip.getDepartureCity() + " → " + trip.getArrivalCity() + " on " +
                         trip.getDateTime().toLocalDate() + ".");
            String link = request.getContextPath() + "/driver/trips/details?id=" + trip.getId() +
                          "&highlightBooking=" + booking.getId();
            notification.setMetadata("{\"cta\":\"Review booking\",\"url\":\"" + link + "\"}");
            
            NotificationDAO notifDAO = new NotificationDAOImpl(conn);
            notifDAO.create(notification);

            if (bookingDAO.create(booking)) {
                response.sendRedirect(request.getContextPath() + "/passenger/dashboard?bookingCreated=1");
            } else {
                throw new Exception("Failed to create booking");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/passenger/trips").forward(request, response);
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