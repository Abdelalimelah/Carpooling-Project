package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JAVA.Beans.AdminBean;
import com.JAVA.DAO.AdminDAO;
import com.JAVA.DAO.Implement.AdminDAOImpl;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Exception.DAOConfigurationException;

@WebServlet("/loginAdmin")
public class LoginAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection connection = null;
        try {
            // Get connection from DAOFactory
            DAOFactory daoFactory = DAOFactory.getInstance();
            connection = daoFactory.getConnection();
            
            AdminDAO adminDAO = new AdminDAOImpl(connection);
            
            if (adminDAO.validateLogin(username, password)) {
                AdminBean admin = adminDAO.findByUsername(username);
                
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("role", "admin");
                
                response.sendRedirect("/views/admin/dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/views/auth/loginAdmin.jsp").forward(request, response);
            }
            
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database configuration error: " + e.getMessage());
            request.getRequestDispatcher("/views/auth/loginAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Login error: " + e.getMessage());
            request.getRequestDispatcher("/views/auth/loginAdmin.jsp").forward(request, response);
        } finally {
            // Close connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/loginAdmin.jsp").forward(request, response);
    }
}