package com.JAVA.Servlet;

import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Implement.FeedbackDAOImpl;
import com.JAVA.DAO.Exception.DAOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/feedbacks")
public class AdminFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (!"delete".equals(action)) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?error=Unknown%20action");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id").trim());
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?error=Invalid%20feedback%20id");
            return;
        }

        Connection conn = null;
        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();
            conn.setAutoCommit(false);

            FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl(conn);

            boolean ok = feedbackDAO.delete(id); 
            if (!ok) throw new DAOException(new RuntimeException("Delete returned false"));

            conn.commit();
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?success=Feedback%20deleted");
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?error=Failed%20to%20delete%20feedback");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }
}