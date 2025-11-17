package com.JAVA.DAO.Implement;

import com.JAVA.Beans.TripBean;
import com.JAVA.DAO.TripDAO;
import com.JAVA.DAO.Exception.DAOException;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TripDAOImpl implements TripDAO {
    
    private Connection connection;

    public TripDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    public List<TripBean> findByDriverId(int driverId) throws DAOException {
        List<TripBean> trips = new ArrayList<>();
        String sql = "SELECT * FROM Trip WHERE driver_id = ? ORDER BY date_time DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TripBean trip = new TripBean();
                trip.setId(rs.getInt("id"));
                trip.setDepartureCity(rs.getString("departure_city"));
                trip.setArrivalCity(rs.getString("arrival_city"));
                trip.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                trip.setPrice(rs.getBigDecimal("price"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setDriverId(rs.getInt("driver_id"));
                trip.setCarId(rs.getInt("car_id"));
                trips.add(trip);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding trips by driver ID: " + driverId, e);
        }
        return trips;
    }
    
    public boolean create(TripBean trip) throws DAOException {
        String sql = "INSERT INTO Trip (departure_city, arrival_city, date_time, price, seats_available, driver_id, car_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trip.getDepartureCity());
            stmt.setString(2, trip.getArrivalCity());
            stmt.setTimestamp(3, Timestamp.valueOf(trip.getDateTime()));
            stmt.setBigDecimal(4, trip.getPrice());
            stmt.setInt(5, trip.getSeatsAvailable());
            stmt.setInt(6, trip.getDriverId());
            stmt.setInt(7, trip.getCarId());
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    trip.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Error creating trip", e);
        }
    }

    public TripBean findById(int id) throws DAOException {
        String sql = "SELECT * FROM Trip WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TripBean trip = new TripBean();
                trip.setId(rs.getInt("id"));
                trip.setDepartureCity(rs.getString("departure_city"));
                trip.setArrivalCity(rs.getString("arrival_city"));
                trip.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                trip.setPrice(rs.getBigDecimal("price"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setDriverId(rs.getInt("driver_id"));
                trip.setCarId(rs.getInt("car_id"));
                return trip;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding trip by ID: " + id, e);
        }
        return null;
    }

    public boolean update(TripBean trip) throws DAOException {
        String sql = "UPDATE Trip SET departure_city = ?, arrival_city = ?, date_time = ?, price = ?, seats_available = ?, car_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trip.getDepartureCity());
            stmt.setString(2, trip.getArrivalCity());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(trip.getDateTime()));
            stmt.setBigDecimal(4, trip.getPrice());
            stmt.setInt(5, trip.getSeatsAvailable());
            stmt.setInt(6, trip.getCarId());
            stmt.setInt(7, trip.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating trip with ID: " + trip.getId(), e);
        }
    }

    public boolean delete(int id) throws DAOException {
        String sql = "DELETE FROM Trip WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error deleting trip with ID: " + id, e);
        }
    }
    
    public List<TripBean> findAvailableTripsForPassenger(
            int passengerId, 
            String departureCity, 
            String arrivalCity, 
            String dateStr) throws DAOException {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.* FROM Trip t ");
        sql.append("WHERE t.date_time > NOW() "); 
        
        sql.append("AND t.id NOT IN (");
        sql.append("    SELECT b.trip_id FROM Booking b WHERE b.passenger_id = ?");
        sql.append(") ");
        
        List<Object> params = new ArrayList<>();
        params.add(passengerId); 
        
        if (departureCity != null && !departureCity.trim().isEmpty()) {
            sql.append("AND t.departure_city LIKE ? ");
            params.add("%" + departureCity.trim() + "%");
        }
        if (arrivalCity != null && !arrivalCity.trim().isEmpty()) {
            sql.append("AND t.arrival_city LIKE ? ");
            params.add("%" + arrivalCity.trim() + "%");
        }
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            sql.append("AND DATE(t.date_time) = ? ");
            params.add(dateStr.trim());
        }
        
        sql.append("ORDER BY t.date_time ASC");
        
        List<TripBean> trips = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TripBean trip = new TripBean();
                trip.setId(rs.getInt("id"));
                trip.setDepartureCity(rs.getString("departure_city"));
                trip.setArrivalCity(rs.getString("arrival_city"));
                trip.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                trip.setPrice(rs.getBigDecimal("price"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setDriverId(rs.getInt("driver_id"));
                trip.setCarId(rs.getInt("car_id"));
                trips.add(trip);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding available trips for passenger: " + passengerId, e);
        }
        return trips;
    }
    
    @Override
    public int countAll() throws DAOException {
        String sql = "SELECT COUNT(*) FROM Trip";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new DAOException(e); }
    }
    
    @Override
    public int countFiltered(String status, String dateStr) throws DAOException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) AS c FROM Trip t");
        java.util.List<String> cond = new java.util.ArrayList<>();
        java.util.List<Object> params = new java.util.ArrayList<>();

        // status filter
        if (status != null) {
            switch (status) {
                case "COMPLETED":
                    cond.add("EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
                case "UPCOMING":
                    cond.add("t.date_time >= NOW()");
                    cond.add("NOT EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
                case "PASSED":
                    cond.add("t.date_time < NOW()");
                    cond.add("NOT EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
            }
        }

        // date filter
        if (dateStr != null) {
            cond.add("DATE(t.date_time) = ?");
            params.add(dateStr);
        }

        if (!cond.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", cond));
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int idx = 1;
            for (Object p : params) {
                ps.setObject(idx++, p);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("c") : 0;
            }
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public java.util.List<com.JAVA.Beans.TripBean> findPagedFiltered(
            String status, String dateStr, int offset, int limit) throws DAOException {

        StringBuilder sql = new StringBuilder(
            "SELECT t.id, t.departure_city, t.arrival_city, t.date_time, " +
            "       t.price, t.seats_available, t.driver_id, t.car_id, t.created_at " +
            "FROM Trip t");
        java.util.List<String> cond = new java.util.ArrayList<>();
        java.util.List<Object> params = new java.util.ArrayList<>();

        if (status != null) {
            switch (status) {
                case "COMPLETED":
                    cond.add("EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
                case "UPCOMING":
                    cond.add("t.date_time >= NOW()");
                    cond.add("NOT EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
                case "PASSED":
                    cond.add("t.date_time < NOW()");
                    cond.add("NOT EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED')");
                    break;
            }
        }

        if (dateStr != null) {
            cond.add("DATE(t.date_time) = ?");
            params.add(dateStr);
        }

        if (!cond.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", cond));
        }
        sql.append(" ORDER BY t.date_time DESC LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int idx = 1;
            for (Object p : params) {
                ps.setObject(idx++, p);
            }
            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);

            java.util.List<com.JAVA.Beans.TripBean> list = new java.util.ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.JAVA.Beans.TripBean t = new com.JAVA.Beans.TripBean();
                    t.setId(rs.getInt("id"));
                    t.setDepartureCity(rs.getString("departure_city"));
                    t.setArrivalCity(rs.getString("arrival_city"));
                    t.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                    t.setPrice(rs.getBigDecimal("price"));
                    t.setSeatsAvailable(rs.getInt("seats_available"));
                    t.setDriverId(rs.getInt("driver_id"));
                    t.setCarId(rs.getInt("car_id"));
                    list.add(t);
                }
            }
            return list;
        } catch (SQLException e) { throw new DAOException(e); }
    }

    @Override
    public java.util.Map<Integer,String> findDerivedStatusForTripIds(
            java.util.Collection<Integer> tripIds) throws DAOException {
        java.util.Map<Integer,String> out = new java.util.HashMap<>();
        if (tripIds == null || tripIds.isEmpty()) return out;

        StringBuilder sb = new StringBuilder(
            "SELECT t.id, " +
            " CASE " +
            "  WHEN EXISTS (SELECT 1 FROM Booking b WHERE b.trip_id = t.id AND b.status = 'COMPLETED') THEN 'COMPLETED' " +
            "  WHEN t.date_time < NOW() THEN 'PASSED' " +
            "  ELSE 'UPCOMING' " +
            " END AS derived_status " +
            "FROM Trip t WHERE t.id IN (");

        StringJoiner ph = new StringJoiner(",");
        for (int i = 0; i < tripIds.size(); i++) ph.add("?");
        sb.append(ph).append(")");

        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {
            int idx = 1;
            for (Integer id : tripIds) ps.setInt(idx++, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.put(rs.getInt("id"), rs.getString("derived_status"));
                }
            }
            return out;
        } catch (SQLException e) { throw new DAOException(e); }
    }
}