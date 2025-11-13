package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.*;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.*; 
import com.JAVA.Beans.*;
import com.JAVA.Utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        if (email == null || email.trim().isEmpty() ||
            password == null || password.length() < 6 ||
            firstName == null || lastName == null) {
            request.setAttribute("errorMessage", "Please fill all required fields.");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            conn.setAutoCommit(false); 

            UserDAO userDAO = new UserDAOImpl(conn);
            if (userDAO.emailExists(email)) {
                request.setAttribute("errorMessage", "Email already registered.");
                request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
                return;
            }

            String hashedPassword = PasswordUtil.hashPasswordWithSalt(password);
            

            UserBean user = new UserBean();
            user.setEmail(email);
            user.setPasswordHash(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);

            if (!userDAO.register(user)) {
                throw new Exception("Failed to register user");
            }

            if ("DRIVER".equals(role)) {
                String licenceNumber = request.getParameter("licenceNumber");
                String carBrand = request.getParameter("carBrand");
                String carModel = request.getParameter("carModel");
                String plateNumber = request.getParameter("plateNumber");

                if (licenceNumber == null || licenceNumber.trim().isEmpty() ||
                    carBrand == null || carModel == null || plateNumber == null) {
                    throw new Exception("Missing driver or car info");
                }

                DriverDAO driverDAO = new DriverDAOImpl(conn);
                DriverBean driver = new DriverBean();
                driver.setUserId(user.getId());
                driver.setLicenceNumber(licenceNumber.trim());
                
                if (!driverDAO.create(driver)) {
                    throw new Exception("Failed to create driver profile");
                }

                CarDAO carDAO = new CarDAOImpl(conn);
                CarBean car = new CarBean();
                car.setDriverId(driver.getId());
                car.setBrand(carBrand.trim());
                car.setModel(carModel.trim());
                car.setPlateNumber(plateNumber.trim());
                if (!carDAO.create(car)) {
                    throw new Exception("Failed to register car");
                } 
                
            } else {
                PassengerDAO passengerDAO = new PassengerDAOImpl(conn);
                PassengerBean passenger = new PassengerBean();
                passenger.setUserId(user.getId());
                
                if (!passengerDAO.create(passenger)) {
                    throw new Exception("Failed to create passenger profile");
                }
            }

            conn.commit();
            response.sendRedirect("login?registered=1");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}