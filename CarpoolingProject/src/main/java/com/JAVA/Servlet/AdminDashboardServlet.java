package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.AdminDAOImpl;
import com.JAVA.DAO.Factory.DAOFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            AdminDAOImpl adminDAO = new AdminDAOImpl(conn);

            request.setAttribute("totalUsers", adminDAO.getTotalUsers());
            request.setAttribute("totalDrivers", adminDAO.getTotalDrivers());
            request.setAttribute("totalPassengers", adminDAO.getTotalPassengers());
            request.setAttribute("totalFeedbacks", adminDAO.getTotalFeedbacks());

            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard");
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
}