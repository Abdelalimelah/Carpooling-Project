package com.JAVA.DAO.Implement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.BookingBean;
import com.JAVA.DAO.BookingDAO;
import com.JAVA.DAO.Exception.DAOException;

public class BookingDAOImpl implements BookingDAO {
    private Connection connection;

    public BookingDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    public List<BookingBean> findByTripId(int tripId) throws DAOException {
        List<BookingBean> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Booking WHERE trip_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tripId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingBean booking = new BookingBean();
                booking.setId(rs.getInt("id"));
                booking.setTripId(rs.getInt("trip_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setStatus(rs.getString("status"));
                booking.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding bookings by trip ID: " + tripId, e);
        }
        return bookings;
    }

    public boolean updateStatus(int bookingId, String status) throws DAOException {
        String sql = "UPDATE Booking SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating booking status for ID: " + bookingId, e);
        }
    }
    
    public int countConfirmedBookingsByTripId(int tripId) throws DAOException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE trip_id = ? AND status IN ('CONFIRMED', 'COMPLETED')";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tripId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DAOException("Error counting confirmed bookings for trip ID: " + tripId, e);
        }
    }
    
    public List<BookingBean> findByPassengerId(int passengerId) throws DAOException {
        List<BookingBean> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Booking WHERE passenger_id = ? ORDER BY booking_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingBean booking = new BookingBean();
                booking.setId(rs.getInt("id"));
                booking.setTripId(rs.getInt("trip_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setStatus(rs.getString("status"));
                booking.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding bookings by passenger ID: " + passengerId, e);
        }
        return bookings;
    }
    
    public boolean create(BookingBean booking) throws DAOException {
        String sql = "INSERT INTO Booking (trip_id, passenger_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getTripId());
            stmt.setInt(2, booking.getPassengerId());
            stmt.setString(3, booking.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error creating booking", e);
        }
    }
    
    public BigDecimal calculateTotalEarningsByDriver(int driverId) throws DAOException {
        String sql = "SELECT COALESCE(SUM(t.price), 0) " +
                     "FROM Trip t " +
                     "JOIN Booking b ON t.id = b.trip_id " +
                     "WHERE t.driver_id = ? AND b.status = 'COMPLETED'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error calculating total earnings for driver ID: " + driverId, e);
        }
        return BigDecimal.ZERO;
    }
    
    public void markTripAsCompleted(int tripId) throws DAOException {
        String sql = "UPDATE Booking SET status = 'COMPLETED' WHERE trip_id = ? AND status = 'CONFIRMED'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tripId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error marking trip as completed for trip ID: " + tripId, e);
        }
    }
    
    public boolean tripIsCompleted(int tripId) throws DAOException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE trip_id = ? AND status = 'COMPLETED'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tripId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error checking if trip is completed for trip ID: " + tripId, e);
        }
        return false;
    }
    
    public BookingBean findById(int id) throws DAOException {
        String sql = "SELECT * FROM Booking WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BookingBean booking = new BookingBean();
                booking.setId(rs.getInt("id"));
                booking.setTripId(rs.getInt("trip_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setStatus(rs.getString("status"));
                booking.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());
                return booking;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding booking by ID: " + id, e);
        }
        return null;
    } 

}