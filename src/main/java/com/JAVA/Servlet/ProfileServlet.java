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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if ("/edit".equals(pathInfo)) {
            request.getRequestDispatcher("/views/profile/edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/views/profile/validation.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "New password and confirmation do not match.");
                request.getRequestDispatcher("/views/profile/edit.jsp").forward(request, response);
                return;
            }
            
            if (newPassword.length() < 6) {
                request.setAttribute("errorMessage", "New password must be at least 6 characters long.");
                request.getRequestDispatcher("/views/profile/edit.jsp").forward(request, response);
                return;
            }
        }
        
        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            UserDAO userDAO = new UserDAOImpl(conn);
             
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);
            userDAO.update(user);
            
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                String hashedPassword = PasswordUtil.hashPasswordWithSalt(newPassword);
                userDAO.updatePassword(user.getId(), hashedPassword);
                user.setPasswordHash(hashedPassword);
            }
            
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/profile?updated=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating profile.");
            request.getRequestDispatcher("/views/profile/edit.jsp").forward(request, response);
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