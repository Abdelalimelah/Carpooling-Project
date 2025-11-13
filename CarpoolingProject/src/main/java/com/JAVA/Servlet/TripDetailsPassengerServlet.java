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

@WebServlet("/passenger/trips/details")
public class TripDetailsPassengerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int tripId = Integer.parseInt(request.getParameter("id"));
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            TripDAO tripDAO = new TripDAOImpl(conn);
            TripBean trip = tripDAO.findById(tripId);
            
            CarDAO carDAO = new CarDAOImpl(conn);
            CarBean car = carDAO.findById(trip.getCarId());
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());
            UserDAO userDAO = new UserDAOImpl(conn);
            UserBean driverUser = userDAO.findById(driverUserId);
            
            int confirmedBookings = bookingDAO.countConfirmedBookingsByTripId(tripId);
            int seatsLeft = trip.getSeatsAvailable() - confirmedBookings;
            
            request.setAttribute("trip", trip);
            request.setAttribute("car", car);
            request.setAttribute("driverUser", driverUser);
            request.setAttribute("seatsLeft", seatsLeft);
            
            request.getRequestDispatcher("/views/passenger/tripDetails.jsp").forward(request, response);
            
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