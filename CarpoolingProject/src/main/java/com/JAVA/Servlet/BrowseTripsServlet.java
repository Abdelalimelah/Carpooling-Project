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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

@WebServlet("/passenger/trips")
public class BrowseTripsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            // Get passenger ID
            PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
            int passengerId = passengerDAO.findByUserId(user.getId());
            
            // Get filter parameters
            String departureCity = request.getParameter("departureCity");
            String arrivalCity = request.getParameter("arrivalCity");
            String dateStr = request.getParameter("date");
            
            // Load available trips (not booked by this passenger, future trips)
            TripDAO tripDAO = new TripDAOImpl(conn);
            List<TripBean> trips = tripDAO.findAvailableTripsForPassenger(
                passengerId, 
                departureCity, 
                arrivalCity, 
                dateStr
            );
            
            // Load related data into maps
            Map<Integer, CarBean> carMap = new HashMap<>();
            Map<Integer, UserBean> driverUserMap = new HashMap<>();
            Map<Integer, Integer> seatsLeftMap = new HashMap<>(); // tripId → seatsLeft
            
            CarDAO carDAO = new CarDAOImpl(conn);
            UserDAO userDAO = new UserDAOImpl(conn);
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            
            for (TripBean trip : trips) {
                // Load car
                CarBean car = carDAO.findById(trip.getCarId());
                carMap.put(trip.getId(), car);
                
                // Load driver user
                int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());
                UserBean driverUser = userDAO.findById(driverUserId);
                driverUserMap.put(trip.getId(), driverUser);
                
                // Calculate seats left
                int confirmedBookings = bookingDAO.countConfirmedBookingsByTripId(trip.getId());
                int seatsLeft = trip.getSeatsAvailable() - confirmedBookings;
                seatsLeftMap.put(trip.getId(), seatsLeft);
            }
            
            // Pass to JSP
            request.setAttribute("trips", trips);
            request.setAttribute("carMap", carMap);
            request.setAttribute("driverUserMap", driverUserMap);
            request.setAttribute("seatsLeftMap", seatsLeftMap);
            request.setAttribute("departureCity", departureCity);
            request.setAttribute("arrivalCity", arrivalCity);
            request.setAttribute("date", dateStr);
            
            request.getRequestDispatcher("/views/passenger/browseTrips.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading trips: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
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