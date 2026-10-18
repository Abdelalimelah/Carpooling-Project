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
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/driver/trips/create")
public class CreateTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");

            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverId = driverDAO.getDriverIdByUserId(user.getId());
            
            CarDAO carDAO = new CarDAOImpl(conn);
            List<CarBean> cars = carDAO.findByDriverId(driverId);
            
            if (cars.isEmpty()) {
                request.setAttribute("errorMessage", "You must add at least one car before publishing a trip.");
                request.getRequestDispatcher("/driver/cars/add").forward(request, response);
                return;
            }
            
            request.setAttribute("cars", cars);
            request.getRequestDispatcher("/views/driver/createTrip.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading trip form.");
            request.getRequestDispatcher("/driver/dashboard").forward(request, response);
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
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");

            String departureCity = request.getParameter("departureCity");
            String arrivalCity = request.getParameter("arrivalCity");
            String dateTimeStr = request.getParameter("dateTime");
            String priceStr = request.getParameter("price");
            String seatsStr = request.getParameter("seatsAvailable");
            String carIdStr = request.getParameter("carId");

            if (departureCity == null || arrivalCity == null || 
                dateTimeStr == null || priceStr == null || 
                seatsStr == null || carIdStr == null ||
                departureCity.trim().isEmpty() || arrivalCity.trim().isEmpty()) {
                throw new Exception("All fields are required.");
            }

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            BigDecimal price = new BigDecimal(priceStr);
            int seatsAvailable = Integer.parseInt(seatsStr);
            int carId = Integer.parseInt(carIdStr);

            if (seatsAvailable <= 0) {
                throw new Exception("Seats available must be at least 1.");
            }
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Price must be greater than 0.");
            }

            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverId = driverDAO.getDriverIdByUserId(user.getId());

            TripBean trip = new TripBean();
            trip.setDepartureCity(departureCity.trim());
            trip.setArrivalCity(arrivalCity.trim());
            trip.setDateTime(dateTime);
            trip.setPrice(price);
            trip.setSeatsAvailable(seatsAvailable);
            trip.setDriverId(driverId);
            trip.setCarId(carId);

            TripDAO tripDAO = new TripDAOImpl(conn);
            if (tripDAO.create(trip)) {
                response.sendRedirect(request.getContextPath() + "/driver/dashboard?tripCreated=1");
            } else {
                throw new Exception("Failed to create trip.");
            }

        } catch (DateTimeParseException e) {
            request.setAttribute("errorMessage", "Invalid date/time format. Use: YYYY-MM-DDTHH:mm");
            forwardWithCars(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format for price or seats.");
            forwardWithCars(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            forwardWithCars(request, response);
        } finally {
            // Fermer la connexion
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void forwardWithCars(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverId = driverDAO.getDriverIdByUserId(user.getId());
            
            CarDAO carDAO = new CarDAOImpl(conn);
            List<CarBean> cars = carDAO.findByDriverId(driverId);
            
            request.setAttribute("cars", cars);
            request.getRequestDispatcher("/views/driver/create-trip.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/driver/dashboard").forward(request, response);
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