package com.JAVA.DAO.Implement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.UserDAO;
import com.JAVA.DAO.Exception.DAOException;

public class UserDAOImpl implements UserDAO {
    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean register(UserBean user) throws DAOException {
        String sql = "INSERT INTO `User` (email, password_hash, first_name, last_name, phone_number, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getRole());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Error registering user with email: " + user.getEmail(), e);
        }
    }

    public boolean emailExists(String email) throws DAOException {
        String sql = "SELECT id FROM `User` WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException("Error checking if email exists: " + email, e);
        }
    }

    public UserBean findByEmail(String email) throws DAOException {
        String sql = "SELECT * FROM `User` WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserBean user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding user by email: " + email, e);
        }
        return null;
    }

    public UserBean findById(int id) throws DAOException {
        String sql = "SELECT id, email, first_name, last_name, phone_number, role FROM `User` WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserBean user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding user by ID: " + id, e);
        }
        return null;
    }

    @Override
    public boolean update(UserBean u) throws DAOException {
        final String sql = "UPDATE `User` " +
                "SET email = ?, first_name = ?, last_name = ?, phone_number = ?, role = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getFirstName());
            ps.setString(3, u.getLastName());
            ps.setString(4, u.getPhoneNumber());
            ps.setString(5, u.getRole());
            ps.setInt(6, u.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public boolean updatePassword(int userId, String hashedPassword) throws DAOException {
        String sql = "UPDATE `User` SET password_hash = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating password for user ID: " + userId, e);
        }
    }

    @Override
    public int countAll() throws DAOException {
        String sql = "SELECT COUNT(*) FROM `User`";
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean deleteById(int userId) throws DAOException {
        String sql = "DELETE FROM `User` WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<UserBean> findPaged(int offset, int limit) throws DAOException {
        String sql = """
                    SELECT id, email, first_name, last_name, phone_number, role, created_at
                    FROM `User`
                    ORDER BY id ASC
                    LIMIT ? OFFSET ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                List<UserBean> list = new ArrayList<>();
                while (rs.next()) {
                    UserBean u = new UserBean();
                    u.setId(rs.getInt("id"));
                    u.setEmail(rs.getString("email"));
                    u.setFirstName(rs.getString("first_name"));
                    u.setLastName(rs.getString("last_name"));
                    u.setPhoneNumber(rs.getString("phone_number"));
                    u.setRole(rs.getString("role"));
                    u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    list.add(u);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
