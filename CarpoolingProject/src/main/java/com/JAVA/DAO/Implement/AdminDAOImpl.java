package com.JAVA.DAO.Implement;

import com.JAVA.DAO.AdminDAO;
import com.JAVA.Beans.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    private Connection connection;

    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getTotalUsers() throws Exception {
        String sql = "SELECT COUNT(*) FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public int getTotalDrivers() throws Exception {
        String sql = "SELECT COUNT(*) FROM drivers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public int getTotalPassengers() throws Exception {
        String sql = "SELECT COUNT(*) FROM passengers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public int getTotalFeedbacks() throws Exception {
        String sql = "SELECT COUNT(*) FROM feedbacks";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public List<DriverBean> getAllDrivers() throws Exception {
        List<DriverBean> drivers = new ArrayList<>();
        String sql = "SELECT d.*, u.firstName, u.lastName, u.email, u.phoneNumber " +
                    "FROM drivers d JOIN users u ON d.userId = u.id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                DriverBean driver = new DriverBean();
                driver.setId(rs.getInt("id"));
                driver.setUserId(rs.getInt("userId"));
                driver.setLicenceNumber(rs.getString("licenceNumber"));
                drivers.add(driver);
            }
        }
        return drivers;
    }

    @Override
    public DriverBean getDriverById(int id) throws Exception {
        String sql = "SELECT d.*, u.firstName, u.lastName, u.email, u.phoneNumber " +
                    "FROM drivers d JOIN users u ON d.userId = u.id WHERE d.id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DriverBean driver = new DriverBean();
                    driver.setId(rs.getInt("id"));
                    driver.setUserId(rs.getInt("userId"));
                    driver.setLicenceNumber(rs.getString("licenceNumber"));
                    return driver;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addDriver(DriverBean driver, UserBean user) throws Exception {
        connection.setAutoCommit(false);
        try {
            // First insert user
            String userSql = "INSERT INTO users (email, password_hash, firstName, lastName, phoneNumber, role) VALUES (?, ?, ?, ?, ?, 'DRIVER')";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, user.getEmail());
                userStmt.setString(2, user.getPasswordHash());
                userStmt.setString(3, user.getFirstName());
                userStmt.setString(4, user.getLastName());
                userStmt.setString(5, user.getPhoneNumber());
                userStmt.executeUpdate();
                
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        // Then insert driver
                        String driverSql = "INSERT INTO drivers (userId, licenceNumber) VALUES (?, ?)";
                        try (PreparedStatement driverStmt = connection.prepareStatement(driverSql)) {
                            driverStmt.setInt(1, userId);
                            driverStmt.setString(2, driver.getLicenceNumber());
                            driverStmt.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateDriver(DriverBean driver, UserBean user) throws Exception {
        connection.setAutoCommit(false);
        try {
            // Update user
            String userSql = "UPDATE users SET email=?, firstName=?, lastName=?, phoneNumber=? WHERE id=?";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setString(1, user.getEmail());
                userStmt.setString(2, user.getFirstName());
                userStmt.setString(3, user.getLastName());
                userStmt.setString(4, user.getPhoneNumber());
                userStmt.setInt(5, driver.getUserId());
                userStmt.executeUpdate();
            }
            
            // Update driver
            String driverSql = "UPDATE drivers SET licenceNumber=? WHERE id=?";
            try (PreparedStatement driverStmt = connection.prepareStatement(driverSql)) {
                driverStmt.setString(1, driver.getLicenceNumber());
                driverStmt.setInt(2, driver.getId());
                driverStmt.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean deleteDriver(int driverId) throws Exception {
        connection.setAutoCommit(false);
        try {
            // First get user ID
            String getUserIdSql = "SELECT userId FROM drivers WHERE id = ?";
            int userId;
            try (PreparedStatement stmt = connection.prepareStatement(getUserIdSql)) {
                stmt.setInt(1, driverId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("userId");
                    } else {
                        return false;
                    }
                }
            }
            
            // Delete driver
            String deleteDriverSql = "DELETE FROM drivers WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteDriverSql)) {
                stmt.setInt(1, driverId);
                stmt.executeUpdate();
            }
            
            // Delete user
            String deleteUserSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserSql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Similar implementations for Passenger and Feedback methods...
    @Override
    public List<PassengerBean> getAllPassengers() throws Exception {
        List<PassengerBean> passengers = new ArrayList<>();
        String sql = "SELECT p.*, u.firstName, u.lastName, u.email, u.phoneNumber " +
                    "FROM passengers p JOIN users u ON p.userId = u.id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PassengerBean passenger = new PassengerBean();
                passenger.setId(rs.getInt("id"));
                passenger.setUserId(rs.getInt("userId"));
                passengers.add(passenger);
            }
        }
        return passengers;
    }

    @Override
    public PassengerBean getPassengerById(int id) throws Exception {
        String sql = "SELECT p.*, u.firstName, u.lastName, u.email, u.phoneNumber " +
                    "FROM passengers p JOIN users u ON p.userId = u.id WHERE p.id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PassengerBean passenger = new PassengerBean();
                    passenger.setId(rs.getInt("id"));
                    passenger.setUserId(rs.getInt("userId"));
                    return passenger;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addPassenger(PassengerBean passenger, UserBean user) throws Exception {
        connection.setAutoCommit(false);
        try {
            // First insert user
            String userSql = "INSERT INTO users (email, password_hash, firstName, lastName, phoneNumber, role) VALUES (?, ?, ?, ?, ?, 'PASSENGER')";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, user.getEmail());
                userStmt.setString(2, user.getPasswordHash());
                userStmt.setString(3, user.getFirstName());
                userStmt.setString(4, user.getLastName());
                userStmt.setString(5, user.getPhoneNumber());
                userStmt.executeUpdate();
                
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        // Then insert passenger
                        String passengerSql = "INSERT INTO passengers (userId) VALUES (?)";
                        try (PreparedStatement passengerStmt = connection.prepareStatement(passengerSql)) {
                            passengerStmt.setInt(1, userId);
                            passengerStmt.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updatePassenger(PassengerBean passenger, UserBean user) throws Exception {
        connection.setAutoCommit(false);
        try {
            // Update user
            String userSql = "UPDATE users SET email=?, firstName=?, lastName=?, phoneNumber=? WHERE id=?";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setString(1, user.getEmail());
                userStmt.setString(2, user.getFirstName());
                userStmt.setString(3, user.getLastName());
                userStmt.setString(4, user.getPhoneNumber());
                userStmt.setInt(5, passenger.getUserId());
                userStmt.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean deletePassenger(int passengerId) throws Exception {
        connection.setAutoCommit(false);
        try {
            // First get user ID
            String getUserIdSql = "SELECT userId FROM passengers WHERE id = ?";
            int userId;
            try (PreparedStatement stmt = connection.prepareStatement(getUserIdSql)) {
                stmt.setInt(1, passengerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("userId");
                    } else {
                        return false;
                    }
                }
            }
            
            // Delete passenger
            String deletePassengerSql = "DELETE FROM passengers WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deletePassengerSql)) {
                stmt.setInt(1, passengerId);
                stmt.executeUpdate();
            }
            
            // Delete user
            String deleteUserSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserSql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<FeedbackBean> getAllFeedbacks() throws Exception {
        List<FeedbackBean> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, u1.firstName as authorFirstName, u1.lastName as authorLastName, " +
                    "u2.firstName as targetFirstName, u2.lastName as targetLastName " +
                    "FROM feedbacks f " +
                    "JOIN users u1 ON f.authorId = u1.id " +
                    "JOIN users u2 ON f.targetId = u2.id " +
                    "ORDER BY f.dateTime DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FeedbackBean feedback = new FeedbackBean();
                feedback.setId(rs.getInt("id"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                feedback.setAuthorId(rs.getInt("authorId"));
                feedback.setTargetId(rs.getInt("targetId"));
                feedback.setFeedbackType(rs.getString("feedbackType"));
                feedback.setTripId(rs.getInt("tripId"));
                feedback.setBookingId(rs.getInt("bookingId"));
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    @Override
    public FeedbackBean getFeedbackById(int id) throws Exception {
        String sql = "SELECT * FROM feedbacks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FeedbackBean feedback = new FeedbackBean();
                    feedback.setId(rs.getInt("id"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setComment(rs.getString("comment"));
                    feedback.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                    feedback.setAuthorId(rs.getInt("authorId"));
                    feedback.setTargetId(rs.getInt("targetId"));
                    feedback.setFeedbackType(rs.getString("feedbackType"));
                    feedback.setTripId(rs.getInt("tripId"));
                    feedback.setBookingId(rs.getInt("bookingId"));
                    return feedback;
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteFeedback(int feedbackId) throws Exception {
        String sql = "DELETE FROM feedbacks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            return stmt.executeUpdate() > 0;
        }
    }
}