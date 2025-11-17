package com.JAVA.DAO.Implement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
    
    @Override
    public int countAll() throws DAOException {
        String sql = "SELECT COUNT(*) FROM Booking";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public int countByStatus(String status) throws DAOException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE status = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public java.math.BigDecimal sumCompletedEarnings() throws DAOException {
        String sql = """
            SELECT COALESCE(SUM(t.price), 0)
            FROM Booking b
            JOIN Trip t ON t.id = b.trip_id
            WHERE b.status = 'COMPLETED'
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal(1) : java.math.BigDecimal.ZERO;
        } catch (SQLException e) { throw new DAOException(e); }
    }
    
    @Override
    public int countFiltered(Integer tripId, Integer passengerId, String status) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Booking b WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tripId != null) { sql.append(" AND b.trip_id = ?"); params.add(tripId); }
        if (passengerId != null) { sql.append(" AND b.passenger_id = ?"); params.add(passengerId); }
        if (status != null) { sql.append(" AND b.status = ?"); params.add(status); }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public List<BookingBean> findPagedFiltered(Integer tripId, Integer passengerId, String status,
                                               int offset, int limit) throws DAOException {
        StringBuilder sql = new StringBuilder(
                "SELECT b.id, b.trip_id, b.passenger_id, b.status, b.booking_date " +
                "FROM Booking b WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (tripId != null) { sql.append(" AND b.trip_id = ?"); params.add(tripId); }
        if (passengerId != null) { sql.append(" AND b.passenger_id = ?"); params.add(passengerId); }
        if (status != null) { sql.append(" AND b.status = ?"); params.add(status); }

        sql.append(" ORDER BY b.id DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            List<BookingBean> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingBean b = new BookingBean();
                    b.setId(rs.getInt("id"));
                    b.setTripId(rs.getInt("trip_id"));
                    b.setPassengerId(rs.getInt("passenger_id"));
                    b.setStatus(rs.getString("status"));
                    Timestamp ts = rs.getTimestamp("booking_date");
                    if (ts != null) b.setBookingDate(ts.toLocalDateTime());
                    list.add(b);
                }
            }
            return list;
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public boolean deleteById(int id) throws DAOException {
        String sql = "DELETE FROM Booking WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new DAOException(e); }
    }
}
