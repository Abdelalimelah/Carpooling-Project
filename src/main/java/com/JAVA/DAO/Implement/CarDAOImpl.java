package com.JAVA.DAO.Implement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.CarBean;
import com.JAVA.DAO.CarDAO;
import com.JAVA.DAO.Exception.DAOException;

public class CarDAOImpl implements CarDAO {
    private Connection connection;

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean create(CarBean car) throws DAOException {
        String sql = "INSERT INTO Car (brand, model, plate_number, driver_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getPlateNumber());
            stmt.setInt(4, car.getDriverId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error creating car", e);
        }
    }
    

    public List<CarBean> findByDriverId(int driverId) throws DAOException {
        List<CarBean> cars = new ArrayList<>();
        String sql = "SELECT * FROM Car WHERE driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CarBean car = new CarBean();
                car.setId(rs.getInt("id"));
                car.setDriverId(rs.getInt("driver_id"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setPlateNumber(rs.getString("plate_number"));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding cars by driver ID: " + driverId, e);
        }
        return cars;
    }

    public CarBean findById(int id) throws DAOException {
        String sql = "SELECT * FROM Car WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CarBean car = new CarBean();
                car.setId(rs.getInt("id"));
                car.setDriverId(rs.getInt("driver_id"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setPlateNumber(rs.getString("plate_number"));
                return car;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding car by ID: " + id, e);
        }
        return null;
    }

    public boolean update(CarBean car) throws DAOException {
        String sql = "UPDATE Car SET brand = ?, model = ?, plate_number = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getPlateNumber());
            stmt.setInt(4, car.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating car with ID: " + car.getId(), e);
        }
    }

    public boolean delete(int id) throws DAOException {
        String sql = "DELETE FROM Car WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error deleting car with ID: " + id, e);
        }
    }
}