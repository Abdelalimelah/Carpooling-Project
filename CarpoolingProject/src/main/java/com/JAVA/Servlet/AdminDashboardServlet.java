package com.JAVA.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import com.JAVA.DAO.*;
import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Implement.BookingDAOImpl;
import com.JAVA.DAO.Implement.FeedbackDAOImpl;
import com.JAVA.DAO.Implement.TripDAOImpl;
import com.JAVA.DAO.Implement.UserDAOImpl;
import com.JAVA.Beans.*;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
    }

    
}
