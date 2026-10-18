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

@WebServlet("/driver/cars/edit")
public class EditCarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            CarDAO carDAO = new CarDAOImpl(conn);
            CarBean car = carDAO.findById(carId);
            
            if (car == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
                return;
            }
            
            request.setAttribute("car", car);
            request.getRequestDispatcher("/views/driver/editCarForm.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading car");
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
            int carId = Integer.parseInt(request.getParameter("id"));
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            String plateNumber = request.getParameter("plateNumber");

            if (brand == null || model == null || plateNumber == null ||
                brand.trim().isEmpty() || model.trim().isEmpty() || plateNumber.trim().isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required.");
                CarBean tempCar = new CarBean();
                tempCar.setId(carId);
                tempCar.setBrand(brand);
                tempCar.setModel(model);
                tempCar.setPlateNumber(plateNumber);
                request.setAttribute("car", tempCar);
                request.getRequestDispatcher("/views/driver/editCarForm.jsp").forward(request, response);
                return;
            }

            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            CarDAO carDAO = new CarDAOImpl(conn);
            CarBean car = carDAO.findById(carId);
            
            if (car == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
                return;
            }

            car.setBrand(brand.trim());
            car.setModel(model.trim());
            car.setPlateNumber(plateNumber.trim());
            
            if (carDAO.update(car)) {
                response.sendRedirect(request.getContextPath() + "/driver/dashboard?carUpdated=1");
            } else {
                throw new Exception("Update failed");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating car: " + e.getMessage());
            request.getRequestDispatcher("/views/driver/editCarForm.jsp").forward(request, response);
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