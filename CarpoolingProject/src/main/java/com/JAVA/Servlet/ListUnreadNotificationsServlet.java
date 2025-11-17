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

@WebServlet("/notifications/unread")
public class ListUnreadNotificationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            HttpSession session = req.getSession();
            UserBean user = (UserBean) session.getAttribute("user");
            int userId = user.getId();

            int limit = parseOrDefault(req.getParameter("limit"), 10);
            int offset = parseOrDefault(req.getParameter("offset"), 0);

            resp.setContentType("application/json");
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            NotificationDAO dao = new NotificationDAOImpl(conn);
            int count = dao.countUnread(userId);
            var items = dao.getUnreadByUser(userId, limit, offset);

            String json = toJson(count, items);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.sendError(500, "Failed to load notifications");
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

    private int parseOrDefault(String s, int def) {
        try { return s == null ? def : Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private String escape(String s) { return s == null ? "" : s.replace("\"","\\\""); }

    private String toJson(int count, java.util.List<NotificationBean> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"unreadCount\":").append(count).append(",\"items\":[");
        for (int i=0; i<items.size(); i++) {
            var n = items.get(i);
            sb.append("{")
              .append("\"id\":").append(n.getId()).append(",")
              .append("\"type\":\"").append(escape(n.getType())).append("\",")
              .append("\"message\":\"").append(escape(n.getMessage())).append("\",")
              .append("\"createdAt\":\"").append(n.getCreatedAt()).append("\",")
              .append("\"metadata\":").append(n.getMetadata() == null ? "null" : n.getMetadata())
              .append("}");
            if (i < items.size()-1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }
}