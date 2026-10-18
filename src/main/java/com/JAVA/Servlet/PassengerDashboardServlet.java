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
import java.util.*;

@WebServlet("/passenger/dashboard")
public class PassengerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
            int passengerId = passengerDAO.findByUserId(user.getId());

            BookingDAO bookingDAO = new BookingDAOImpl(conn);
            List<BookingBean> bookings = bookingDAO.findByPassengerId(passengerId);

            Map<Integer, TripBean> tripMap = new HashMap<>();
            Map<Integer, UserBean> driverUserMap = new HashMap<>();
            Map<Integer, CarBean> carMap = new HashMap<>();

            TripDAO tripDAO = new TripDAOImpl(conn);
            UserDAO userDAO = new UserDAOImpl(conn);
            CarDAO carDAO = new CarDAOImpl(conn);
            DriverDAO driverDAO = new DriverDAOImpl(conn);

            for (BookingBean booking : bookings) {
                TripBean trip = tripDAO.findById(booking.getTripId());
                if (trip != null) {
                    tripMap.put(booking.getTripId(), trip);

                    int driverUserId = driverDAO.findUserIdByDriverId(trip.getDriverId());
                    UserBean driverUser = userDAO.findById(driverUserId);
                    driverUserMap.put(trip.getDriverId(), driverUser);

                    CarBean car = carDAO.findById(trip.getCarId());
                    carMap.put(trip.getCarId(), car);
                }
            }

            int unreadCount = 0;
            List<NotificationBean> notifications = new ArrayList<>();
            try {
                NotificationDAO notifDAO = new NotificationDAOImpl(conn);
                unreadCount = notifDAO.countUnread(user.getId());
                notifications = notifDAO.getUnreadByUser(user.getId(), 10, 0);
            } catch (Exception e) {
                System.err.println("Notifications error (non-blocking): " + e.getMessage());
            }
            request.setAttribute("bookings", bookings);
            request.setAttribute("tripMap", tripMap);
            request.setAttribute("driverUserMap", driverUserMap);
            request.setAttribute("carMap", carMap);
            request.setAttribute("user", user);
            request.setAttribute("unreadNotificationCount", unreadCount);
            request.setAttribute("notifications", notifications);

            request.getRequestDispatcher("/views/passenger/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard.");
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