package com.JAVA.Servlet;

import com.JAVA.DAO.Factory.DAOFactory;
import com.JAVA.DAO.Implement.BookingDAOImpl;
import com.JAVA.DAO.Exception.DAOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/bookings")
public class AdminBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        int id;
        try {
            id = Integer.parseInt(String.valueOf(req.getParameter("id")).trim());
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard?error=Invalid%20booking%20id");
            return;
        }

        Connection conn = null;
        try {
            DAOFactory f = DAOFactory.getInstance();
            conn = f.getConnection();
            conn.setAutoCommit(false);

            BookingDAOImpl bookingDAO = new BookingDAOImpl(conn);
            boolean ok;

            switch (action) {
                case "confirm":
                    ok = bookingDAO.updateStatus(id, "CONFIRMED");
                    break;
                case "cancel":
                    ok = bookingDAO.updateStatus(id, "CANCELLED");
                    break;
                case "delete":
                    ok = bookingDAO.deleteById(id);
                    break;
                default:
                    ok = false;
            }

            if (!ok) throw new DAOException(new RuntimeException("Operation failed"));

            conn.commit();

            // Preserve current booking filters/pagination if they’re sent as hidden fields
            String qs = buildReturnQS(req);
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard" + qs);

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard?error=Failed%20to%20update%20booking");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }

    private String buildReturnQS(HttpServletRequest req) {
        String bStatus = req.getParameter("b_status");
        String bTripId = req.getParameter("b_tripId");
        String bPassengerId = req.getParameter("b_passengerId");
        String bPage = req.getParameter("b_page");

        StringBuilder sb = new StringBuilder("?");
        boolean first = true;
        first = append(sb, "b_status", bStatus, first);
        first = append(sb, "b_tripId", bTripId, first);
        first = append(sb, "b_passengerId", bPassengerId, first);
        first = append(sb, "b_page", bPage, first);
        if (first) return ""; // nothing appended
        return sb.toString();
    }

    private boolean append(StringBuilder sb, String name, String val, boolean first) {
        if (val == null || val.isBlank()) return first;
        if (!first) sb.append("&"); else first = false;
        sb.append(name).append("=").append(val.trim());
        return first;
    }
}