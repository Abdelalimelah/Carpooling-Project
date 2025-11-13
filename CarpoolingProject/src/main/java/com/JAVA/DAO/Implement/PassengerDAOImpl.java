package com.JAVA.DAO.Implement;

import java.sql.*; 

import com.JAVA.Beans.PassengerBean;
import com.JAVA.DAO.PassengerDAO;
import com.JAVA.DAO.Exception.DAOException;
  
public class PassengerDAOImpl implements PassengerDAO {
    
    private Connection connection;

    public PassengerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean create(PassengerBean passenger) throws DAOException {
        String sql = "INSERT INTO Passenger (user_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, passenger.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error creating passenger", e);
        }
    }
    
    public int findUserIdByPassengerId(int passengerId) throws DAOException {
        String sql = "SELECT user_id FROM Passenger WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
            throw new DAOException("Passenger not found with ID: " + passengerId);
        } catch (SQLException e) {
            throw new DAOException("Error finding user ID by passenger ID: " + passengerId, e);
        }
    }
    
    public int findByUserId(int userId) throws DAOException {
        String sql = "SELECT id FROM Passenger WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new DAOException("Passenger profile not found for user ID: " + userId);
        } catch (SQLException e) {
            throw new DAOException("Error finding passenger by user ID: " + userId, e);
        }
    }
    
    public long countAll() throws DAOException {
        String sql = "SELECT COUNT(*) FROM Passenger"; 
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) { throw new DAOException(e); }
    }
}