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
import java.util.*;

@WebServlet("/driver/trips/details")
public class TripDetailsServlet extends HttpServlet {
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
            if (trip == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trip not found");
                return;
            }
            
            CarDAO carDAO = new CarDAOImpl(conn);
            CarBean car = carDAO.findById(trip.getCarId());
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            int confirmedBookings = bookingDAO.countConfirmedBookingsByTripId(tripId);
            int seatsLeft = trip.getSeatsAvailable() - confirmedBookings;
            
            List<BookingBean> bookings = bookingDAO.findByTripId(tripId);
            
            Map<Integer, UserBean> passengerUserMap = new HashMap<>();
            PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
            UserDAO userDAO = new UserDAOImpl(conn);
            
            for (BookingBean booking : bookings) {
                int userId = passengerDAO.findUserIdByPassengerId(booking.getPassengerId());
                UserBean passengerUser = userDAO.findById(userId);
                passengerUserMap.put(booking.getPassengerId(), passengerUser);
            }
            
            request.setAttribute("trip", trip);
            request.setAttribute("car", car);
            request.setAttribute("bookings", bookings);
            request.setAttribute("passengerUserMap", passengerUserMap); 
            request.setAttribute("seatsLeft", seatsLeft);
            request.getRequestDispatcher("/views/driver/tripDetails.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading trip details");
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