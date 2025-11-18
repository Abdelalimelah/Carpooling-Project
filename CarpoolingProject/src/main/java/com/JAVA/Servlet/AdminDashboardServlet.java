package com.JAVA.Servlet;

import com.JAVA.DAO.Implement.BookingDAOImpl;
import com.JAVA.DAO.Implement.FeedbackDAOImpl;
import com.JAVA.DAO.Implement.PassengerDAOImpl;
import com.JAVA.DAO.Implement.TripDAOImpl;
import com.JAVA.DAO.Implement.UserDAOImpl;
import com.JAVA.Beans.FeedbackBean;
import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.Factory.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        Connection conn = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            conn = daoFactory.getConnection();
            
            HttpSession session = request.getSession(false);
            UserBean user = (UserBean) session.getAttribute("user");
            
            UserDAOImpl userDAO = new UserDAOImpl(conn);
            TripDAOImpl tripDAO = new TripDAOImpl(conn);
            BookingDAOImpl bookingDAO = new BookingDAOImpl(conn);
            FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl(conn);
            
            int totalBookings = bookingDAO.countAll();
            double cancellationRate = totalBookings == 0 ? 0.0 : (bookingDAO.countByStatus("CANCELLED") * 100.0 / totalBookings);

            String formattedCancellationRate = String.format("%.2f", cancellationRate);
            
            BigDecimal totalEarnings = bookingDAO.sumCompletedEarnings();
            
            final int PAGE_SIZE = 5;
            int page = 1;
            try {
                String p = request.getParameter("u_page");
                if (p != null) page = Math.max(1, Integer.parseInt(p));
            } catch (NumberFormatException ignored) {}

            int offset = (page - 1) * PAGE_SIZE;

            int totalUsers = userDAO.countAll();
            List<UserBean> users = userDAO.findPaged(offset, PAGE_SIZE);
            boolean hasNext = offset + PAGE_SIZE < totalUsers;
            
            final int T_PAGE_SIZE = 5;
            
            String tStatus = request.getParameter("t_status");   
            if (tStatus != null && tStatus.isBlank()) tStatus = null;

            String tDate = request.getParameter("t_date");       
            if (tDate != null && tDate.isBlank()) tDate = null;

            int tPage = 1;
            try {
                String tp = request.getParameter("t_page");
                if (tp != null) tPage = Math.max(1, Integer.parseInt(tp));
            } catch (NumberFormatException ignored) {}
            int tOffset = (tPage - 1) * T_PAGE_SIZE;

            int tTotal = tripDAO.countFiltered(tStatus, tDate);
            java.util.List<com.JAVA.Beans.TripBean> trips =
                    tripDAO.findPagedFiltered(tStatus, tDate, tOffset, T_PAGE_SIZE);

            java.util.Set<Integer> ids = new java.util.HashSet<>();
            for (var t : trips) ids.add(t.getId());
            java.util.Map<Integer,String> tripStatus = tripDAO.findDerivedStatusForTripIds(ids);

            boolean tHasNext = (tOffset + trips.size()) < tTotal;
            

            final int F_PAGE_SIZE = 5;

            String fType = request.getParameter("f_type");
            if (fType != null && fType.isBlank()) fType = null;

            Integer fAuthor = null;
            try {
                String s = request.getParameter("f_author");
                if (s != null && !s.isBlank()) fAuthor = Integer.parseInt(s.trim());
            } catch (NumberFormatException ignored) {}

            Integer fTarget = null;
            try {
                String s = request.getParameter("f_target");
                if (s != null && !s.isBlank()) fTarget = Integer.parseInt(s.trim());
            } catch (NumberFormatException ignored) {}

            Integer fMin = null;
            try {
                String s = request.getParameter("f_min");
                if (s != null && !s.isBlank()) fMin = Math.max(1, Integer.parseInt(s.trim()));
            } catch (NumberFormatException ignored) {}

            int fPage = 1;
            try {
                String fp = request.getParameter("f_page");
                if (fp != null) fPage = Math.max(1, Integer.parseInt(fp.trim()));
            } catch (NumberFormatException ignored) {}
            int fOffset = (fPage - 1) * F_PAGE_SIZE;

            int fTotal = feedbackDAO.countFiltered(fType, fAuthor, fTarget, fMin);
            List<FeedbackBean> feedbacks =
                    feedbackDAO.findPagedFiltered(fType, fAuthor, fTarget, fMin, fOffset, F_PAGE_SIZE);

            boolean fHasNext = (fOffset + feedbacks.size()) < fTotal;

            java.util.Map<Integer,String> authorNames = new java.util.HashMap<>();
            java.util.Map<Integer,String> targetNames = new java.util.HashMap<>();

            for (var fb : feedbacks) {
                int aId = fb.getAuthorId();
                if (!authorNames.containsKey(aId)) {
                    var au = userDAO.findById(aId);
                    authorNames.put(aId, au != null
                            ? ( (au.getFirstName()!=null?au.getFirstName():"") + " " + (au.getLastName()!=null?au.getLastName():"") ).trim()
                            : ("#" + aId));
                }
                int tId = fb.getTargetId();
                if (!targetNames.containsKey(tId)) {
                    var tu = userDAO.findById(tId);
                    targetNames.put(tId, tu != null
                            ? ( (tu.getFirstName()!=null?tu.getFirstName():"") + " " + (tu.getLastName()!=null?tu.getLastName():"") ).trim()
                            : ("#" + tId));
                }
            }
            
            final int B_PAGE_SIZE = 5;

            String bStatus = request.getParameter("b_status");
            if (bStatus != null) {
                bStatus = bStatus.trim();
                if (bStatus.isEmpty()) bStatus = null;
            }

            Integer bTripId = null;
            String rawTripId = request.getParameter("b_tripId");
            if (rawTripId != null) {
                rawTripId = rawTripId.trim();
                if (!rawTripId.isEmpty()) {
                    try { bTripId = Integer.valueOf(rawTripId); } catch (NumberFormatException ignored) {}
                }
            }

            Integer bPassengerId = null;
            String rawPassengerId = request.getParameter("b_passengerId");
            if (rawPassengerId != null) {
                rawPassengerId = rawPassengerId.trim();
                if (!rawPassengerId.isEmpty()) {
                    try { bPassengerId = Integer.valueOf(rawPassengerId); } catch (NumberFormatException ignored) {}
                }
            }

            int bPage = 1;
            String rawBPage = request.getParameter("b_page");
            if (rawBPage != null) {
                rawBPage = rawBPage.trim();
                if (!rawBPage.isEmpty()) {
                    try { bPage = Math.max(1, Integer.parseInt(rawBPage)); } catch (NumberFormatException ignored) {}
                }
            }
            int bOffset = (bPage - 1) * B_PAGE_SIZE;

            PassengerDAOImpl passengerDAO = new PassengerDAOImpl(conn);

            int bTotal = bookingDAO.countFiltered(bTripId, bPassengerId, bStatus);
            java.util.List<com.JAVA.Beans.BookingBean> bookings =
                    bookingDAO.findPagedFiltered(bTripId, bPassengerId, bStatus, bOffset, B_PAGE_SIZE);
            boolean bHasNext = (bOffset + bookings.size()) < bTotal;

            java.util.Map<Integer, com.JAVA.Beans.TripBean> bookingTripMap = new java.util.HashMap<>();
            java.util.Map<Integer, String> bookingPassengerNameMap = new java.util.HashMap<>();

            for (var b : bookings) {
                if (!bookingTripMap.containsKey(b.getTripId())) {
                    var t = tripDAO.findById(b.getTripId());
                    if (t != null) bookingTripMap.put(b.getTripId(), t);
                }
                if (!bookingPassengerNameMap.containsKey(b.getPassengerId())) {
                    int uid = passengerDAO.findUserIdByPassengerId(b.getPassengerId());
                    String name = "User #" + uid;
                    var u = userDAO.findById(uid);
                    if (u != null) {
                        String fn = (u.getFirstName() != null) ? u.getFirstName() : "";
                        String ln = (u.getLastName() != null) ? u.getLastName() : "";
                        name = (fn + " " + ln).trim();
                        if (name.isBlank() && u.getEmail() != null) name = u.getEmail();
                    }
                    bookingPassengerNameMap.put(b.getPassengerId(), name);
                }
            }

            request.setAttribute("bookings", bookings);
            request.setAttribute("b_total", bTotal);
            request.setAttribute("b_page", bPage);
            request.setAttribute("b_pageSize", B_PAGE_SIZE);
            request.setAttribute("b_hasNext", bHasNext);
            request.setAttribute("b_status", bStatus);
            request.setAttribute("b_tripId", bTripId);
            request.setAttribute("b_passengerId", bPassengerId);
            request.setAttribute("bookingTripMap", bookingTripMap);
            request.setAttribute("bookingPassengerNameMap", bookingPassengerNameMap);
            
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("authorNames", authorNames);
            request.setAttribute("targetNames", targetNames);
            request.setAttribute("f_type", fType);
            request.setAttribute("f_author", fAuthor);
            request.setAttribute("f_target", fTarget);
            request.setAttribute("f_min", fMin);
            request.setAttribute("f_page", fPage);
            request.setAttribute("f_pageSize", F_PAGE_SIZE);
            request.setAttribute("f_hasNext", fHasNext);
            request.setAttribute("f_total", fTotal);

            request.setAttribute("t_status", tStatus);
            request.setAttribute("t_date", tDate);
            request.setAttribute("t_total", tTotal);
            request.setAttribute("t_page", tPage);
            request.setAttribute("t_pageSize", T_PAGE_SIZE);
            request.setAttribute("t_hasNext", tHasNext);
            request.setAttribute("trips", trips);
            request.setAttribute("tripStatus", tripStatus);
            
            request.setAttribute("totalTrips", tripDAO.countAll());
            request.setAttribute("totalBookings", totalBookings);
            request.setAttribute("totalFeedbacks", feedbackDAO.countAll());
            request.setAttribute("cancellationRate", formattedCancellationRate);
            request.setAttribute("totalEarnings", totalEarnings);
            request.setAttribute("totalUsers", totalUsers);
            
            request.setAttribute("users", users);
            request.setAttribute("u_page", page);
            request.setAttribute("u_pageSize", PAGE_SIZE);
            request.setAttribute("u_hasNext", hasNext);
            request.setAttribute("user", user);
            
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