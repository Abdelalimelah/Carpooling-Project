package com.JAVA.Servlet;
import javax.servlet.*; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

import com.JAVA.DAO.CarDAO;
import com.JAVA.DAO.Implement.CarDAOImpl;
import com.JAVA.DAO.DriverDAO;
import com.JAVA.DAO.Implement.DriverDAOImpl;
import com.JAVA.Beans.CarBean;
import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.Factory.DAOFactory;

@WebServlet("/driver/cars/add")
public class AddCarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/driver/addCarForm.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");

        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String plateNumber = request.getParameter("plateNumber");

        // Validate input
        if (brand == null || brand.trim().isEmpty() ||
            model == null || model.trim().isEmpty() ||
            plateNumber == null || plateNumber.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/views/driver/addCarForm.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        try {
        	DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();

            // Get driver ID from user ID
            DriverDAO driver = new DriverDAOImpl(conn);
            int driverId = driver.getDriverIdByUserId(user.getId());

            // Create car
            CarBean car = new CarBean();
            car.setDriverId(driverId);
            car.setBrand(brand.trim());
            car.setModel(model.trim());
            car.setPlateNumber(plateNumber.trim());

            CarDAO carDAO = new CarDAOImpl(conn);
            if (carDAO.create(car)) {
                response.sendRedirect(request.getContextPath() + "/driver/dashboard?carAdded=1");
            } else {
                throw new Exception("Failed to save car");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding car: " + e.getMessage());
            request.getRequestDispatcher("/views/driver/addCarForm.jsp").forward(request, response);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}