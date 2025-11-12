package com.JAVA.DAO.Implement;

import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.FeedbackBean;
import com.JAVA.DAO.FeedbackDAO;
import com.JAVA.DAO.Exception.DAOException;

public class FeedbackDAOImpl implements FeedbackDAO {
    private Connection connection;

    public FeedbackDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    public boolean create(FeedbackBean feedback) throws DAOException {
        String sql = "INSERT INTO FeedBack (rating, comment, author_id, target_id, feedback_type, trip_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, feedback.getComment());
            stmt.setInt(3, feedback.getAuthorId());
            stmt.setInt(4, feedback.getTargetId());
            stmt.setString(5, feedback.getFeedbackType());
            stmt.setInt(6, feedback.getTripId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error creating feedback", e);
        }
    }

    public boolean existsByAuthorAndTargetAndTrip(int authorId, int targetId, int tripId) throws DAOException {
        String sql = "SELECT 1 FROM FeedBack WHERE author_id = ? AND target_id = ? AND trip_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, authorId);
            stmt.setInt(2, targetId);
            stmt.setInt(3, tripId);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new DAOException("Error checking if feedback exists for author: " + authorId + ", target: " + targetId + ", trip: " + tripId, e);
        }
    }

    public double getAverageRatingForTarget(int targetId, String feedbackType) throws DAOException {
        String sql = "SELECT COALESCE(AVG(rating), 0) FROM FeedBack WHERE target_id = ? AND feedback_type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, targetId);
            stmt.setString(2, feedbackType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error getting average rating for target: " + targetId + ", type: " + feedbackType, e);
        }
        return 0.0;
    }
    
    public List<FeedbackBean> findByTargetAndType(int targetId, String feedbackType) throws DAOException {
        List<FeedbackBean> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM FeedBack WHERE target_id = ? AND feedback_type = ? ORDER BY date_time DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, targetId);
            stmt.setString(2, feedbackType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FeedbackBean fb = new FeedbackBean();
                fb.setId(rs.getInt("id"));
                fb.setRating(rs.getInt("rating"));
                fb.setComment(rs.getString("comment"));
                fb.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                fb.setAuthorId(rs.getInt("author_id"));
                fb.setTargetId(rs.getInt("target_id"));
                fb.setFeedbackType(rs.getString("feedback_type"));
                fb.setTripId(rs.getInt("trip_id"));
                feedbacks.add(fb);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding feedback for target: " + targetId + ", type: " + feedbackType, e);
        }
        return feedbacks;
    }
}