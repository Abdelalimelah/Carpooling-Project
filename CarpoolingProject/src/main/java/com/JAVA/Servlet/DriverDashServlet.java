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
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/driver/dashboard")
public class DriverDashServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");

        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            DriverDAO driverDAO = new DriverDAOImpl(conn);
            int driverId = driverDAO.getDriverIdByUserId(user.getId());
            
            TripDAO tripDAO = new TripDAOImpl(conn);
            List<TripBean> allTrips = tripDAO.findByDriverId(driverId);
            
            List<TripBean> activeTrips = new ArrayList<>();
            List<TripBean> completedTrips = new ArrayList<>();
            
            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            LocalDateTime now = LocalDateTime.now();
            
            for (TripBean trip : allTrips) {
                boolean isCompleted = bookingDAO.tripIsCompleted(trip.getId());
                
                if (isCompleted) {
                    completedTrips.add(trip);
                } else {
                    activeTrips.add(trip);
                }
            }
            
            CarDAO carDAO = new CarDAOImpl(conn);
            List<CarBean> cars = carDAO.findByDriverId(driverId);
            
            int totalTrips = allTrips.size();
            BigDecimal totalEarnings = bookingDAO.calculateTotalEarningsByDriver(driverId);
            
            FeedbackDAO feedbackDAO = new FeedbackDAOImpl(conn);
            double averageRating = feedbackDAO.getAverageRatingForTarget(driverId, "DRIVER");
            
            int unreadCount = 0;
            List<NotificationBean> notifications = new ArrayList<>();
            try {
                NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                unreadCount = notifDAO.countUnread(user.getId());
                notifications = notifDAO.getUnreadByUser(user.getId(), 10, 0);
            } catch (Exception e) {
                System.err.println("Notifications error (non-blocking): " + e.getMessage());
            }
            
            request.setAttribute("cars", cars);
            request.setAttribute("activeTrips", activeTrips);
            request.setAttribute("completedTrips", completedTrips);
            request.setAttribute("totalTrips", totalTrips);
            request.setAttribute("totalEarnings", totalEarnings);
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("user", user);
            request.setAttribute("now", now);
            request.setAttribute("unreadNotificationCount", unreadCount);
            request.setAttribute("notifications", notifications);
            
            request.getRequestDispatcher("/views/driver/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}