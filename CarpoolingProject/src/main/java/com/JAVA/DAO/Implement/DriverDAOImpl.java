package com.JAVA.DAO.Implement;

import java.sql.*;

import com.JAVA.Beans.DriverBean;
import com.JAVA.DAO.DriverDAO;
import com.JAVA.DAO.Exception.DAOException;

public class DriverDAOImpl implements DriverDAO {
    private Connection connection;

    public DriverDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean create(DriverBean driver) throws DAOException {
        String sql = "INSERT INTO Driver (user_id, licence_number) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, driver.getUserId());
            stmt.setString(2, driver.getLicenceNumber());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    driver.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Error creating driver", e);
        }
    }
    
    public int getDriverIdByUserId(int userId) throws DAOException {
        String sql = "SELECT id FROM Driver WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new DAOException("Driver profile not found for user " + userId);
        } catch (SQLException e) {
            throw new DAOException("Error getting driver ID by user ID", e);
        }
    }
    
    public int findUserIdByDriverId(int driverId) throws DAOException {
        String sql = "SELECT user_id FROM Driver WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
            throw new DAOException("Driver not found");
        } catch (SQLException e) {
            throw new DAOException("Error finding user ID by driver ID", e);
        }
    }
}
