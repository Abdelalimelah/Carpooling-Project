package com.JAVA.DAO.Implement;

import com.JAVA.Beans.NotificationBean;  
import java.sql.*;
import java.util.*;
import com.JAVA.DAO.NotificationDAO;
import com.JAVA.DAO.Exception.DAOException;

public class NotificationDAOImpl implements NotificationDAO {
    private final Connection connection;

    public NotificationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public int create(NotificationBean notification) throws DAOException {
        String sql = "INSERT INTO notification (user_id, type, message, metadata) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, notification.getUserId());
            stmt.setString(2, notification.getType());
            stmt.setString(3, notification.getMessage());
            String metadata = notification.getMetadata();
            if (metadata != null && !metadata.trim().isEmpty()) {
                stmt.setString(4, metadata); 
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        notification.setId(id);
                        return id;
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new DAOException("Error creating notification", e);
        }
    }

    public int countUnread(int userId) throws DAOException {
        String sql = "SELECT COUNT(*) FROM notification WHERE user_id = ? AND is_read = FALSE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error counting unread notifications for user: " + userId, e);
        }
    }
    
    public List<NotificationBean> getUnreadByUser(int userId, int limit, int offset) throws DAOException {
        String sql = """
            SELECT id, user_id, type, message, is_read, created_at, metadata
            FROM notification
            WHERE user_id = ? AND is_read = FALSE
            ORDER BY created_at DESC
            LIMIT ? OFFSET ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                return map(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error getting unread notifications for user: " + userId, e);
        }
    }

    // Recent (read + unread) for history view
    public List<NotificationBean> listRecent(int userId, int limit) throws DAOException {
        String sql = """
            SELECT id, user_id, type, message, is_read, created_at, metadata
            FROM notification
            WHERE user_id = ?
            ORDER BY created_at DESC
            LIMIT ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                return map(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing recent notifications for user: " + userId, e);
        }
    }

    public void markAllAsRead(int userId) throws DAOException {
        String sql = "UPDATE notification SET is_read = TRUE WHERE user_id = ? AND is_read = FALSE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error marking all notifications as read for user: " + userId, e);
        }
    }

    public void markAsReadById(int userId, int notificationId) throws DAOException {
        String sql = "UPDATE notification SET is_read = TRUE WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error marking notification as read for ID: " + notificationId + ", user: " + userId, e);
        }
    }

    private List<NotificationBean> map(ResultSet rs) throws SQLException {
        List<NotificationBean> list = new ArrayList<>();
        while (rs.next()) {
            NotificationBean n = new NotificationBean();
            n.setId(rs.getInt("id"));
            n.setUserId(rs.getInt("user_id"));
            n.setType(rs.getString("type"));
            n.setMessage(rs.getString("message"));
            n.setRead(rs.getBoolean("is_read"));

            Timestamp ts = rs.getTimestamp("created_at");
            if (ts != null) n.setCreatedAt(ts.toLocalDateTime());

            String md = rs.getString("metadata");
            n.setMetadata(md); 
            list.add(n);
        }
        return list;
    }
}