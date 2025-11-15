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
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/driver/trips/edit")
public class EditTripServlet extends HttpServlet {
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
            List<CarBean> cars = carDAO.findByDriverId(trip.getDriverId());
            
            request.setAttribute("trip", trip);
            request.setAttribute("cars", cars);
            request.getRequestDispatcher("/views/driver/editTrip.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading trip: " + e.getMessage());
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

            trip.setDepartureCity(request.getParameter("departureCity"));
            trip.setArrivalCity(request.getParameter("arrivalCity"));
            trip.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
            trip.setPrice(new BigDecimal(request.getParameter("price")));
            trip.setSeatsAvailable(Integer.parseInt(request.getParameter("seatsAvailable")));
            trip.setCarId(Integer.parseInt(request.getParameter("carId")));

            if (tripDAO.update(trip)) {
                response.sendRedirect(request.getContextPath() + "/driver/dashboard?tripUpdated=1");
            } else {
                throw new Exception("Update failed");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            
            try {
                int tripId = Integer.parseInt(request.getParameter("id"));
                DAOFactory daoFactory = DAOFactory.getInstance();
                conn = daoFactory.getConnection();
                
                TripDAO tripDAO = new TripDAOImpl(conn);
                TripBean trip = tripDAO.findById(tripId);
                
                if (trip != null) {
                    CarDAO carDAO = new CarDAOImpl(conn);
                    List<CarBean> cars = carDAO.findByDriverId(trip.getDriverId());
                    request.setAttribute("trip", trip);
                    request.setAttribute("cars", cars);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            request.getRequestDispatcher("/views/driver/editTrip.jsp").forward(request, response);
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