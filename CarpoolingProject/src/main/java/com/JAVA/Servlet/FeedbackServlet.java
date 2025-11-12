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
import java.sql.Connection;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tripIdStr = request.getParameter("tripId");
            String targetIdStr = request.getParameter("targetId");
            String type = request.getParameter("type");
            
            if (tripIdStr == null || targetIdStr == null || type == null ||
                (!"DRIVER".equals(type) && !"PASSENGER".equals(type))) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
                return;
            }
            
            int tripId = Integer.parseInt(tripIdStr);
            int targetId = Integer.parseInt(targetIdStr);
            
            request.setAttribute("tripId", tripId);
            request.setAttribute("targetId", targetId);
            request.setAttribute("feedbackType", type);
            
            request.getRequestDispatcher("/views/feedback/form.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading form");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            int targetId = Integer.parseInt(request.getParameter("targetId"));
            String feedbackType = request.getParameter("feedbackType"); 
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");
            
            HttpSession session = request.getSession(false);
            UserBean author = (UserBean) session.getAttribute("user");
            
            if (rating < 1 || rating > 5 || 
                (!"DRIVER".equals(feedbackType) && !"PASSENGER".equals(feedbackType))) {
                throw new Exception("Invalid input");
            }
            
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            FeedbackDAO feedbackDAO = new FeedbackDAOImpl(conn);
            if (feedbackDAO.existsByAuthorAndTargetAndTrip(author.getId(), targetId, tripId)) {
                throw new Exception("You already reviewed this trip");
            }
            
            FeedbackBean feedback = new FeedbackBean();
            feedback.setAuthorId(author.getId());
            feedback.setTargetId(targetId);
            feedback.setFeedbackType(feedbackType);
            feedback.setRating(rating);
            feedback.setComment(comment != null ? comment.trim() : "");
            feedback.setTripId(tripId);
            
            feedbackDAO.create(feedback);
            
            String redirectUrl = "PASSENGER".equals(author.getRole()) 
                ? "/passenger/dashboard" 
                : "/driver/dashboard";
            response.sendRedirect(request.getContextPath() + redirectUrl + "?feedbackSubmitted=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.setAttribute("tripId", request.getParameter("tripId"));
            request.setAttribute("targetId", request.getParameter("targetId"));
            request.setAttribute("feedbackType", request.getParameter("feedbackType"));
            request.getRequestDispatcher("/views/feedback/form.jsp").forward(request, response);
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