package com.JAVA.DAO.Implement;

import com.JAVA.Beans.PassengerBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PassengerDAOImplTest {
    private static Connection connection;
    private PassengerDAOImpl passengerDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_passenger;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                        CREATE TABLE \"User\" (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            email VARCHAR(255),
                            first_name VARCHAR(255),
                            last_name VARCHAR(255),
                            phone_number VARCHAR(255),
                            role VARCHAR(50),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
                        CREATE TABLE Passenger (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT
                        );
                    """);
        }
    }

    @AfterAll
    static void tearDownDatabase() throws Exception {
        connection.close();
    }

    @BeforeEach
    void setUp() throws Exception {
        passengerDAO = new PassengerDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Passenger");
            stmt.execute("DELETE FROM User");
        }
    }

    private int insertUser() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO User (email, first_name, last_name, phone_number, role) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "test@mail.com");
            ps.setString(2, "First");
            ps.setString(3, "Last");
            ps.setString(4, "1234567890");
            ps.setString(5, "PASSENGER");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        }
        throw new SQLException("User insert failed");
    }

    @Test
    void testCreateAndFindById() throws Exception {
        int userId = insertUser();
        PassengerBean passenger = new PassengerBean();
        passenger.setUserId(userId);
        assertTrue(passengerDAO.create(passenger));
        List<PassengerBean> all = passengerDAO.findAll();
        assertFalse(all.isEmpty());
        PassengerBean found = passengerDAO.findById(all.get(0).getId());
        assertNotNull(found);
        assertEquals(userId, found.getUserId());
    }

    @Test
    void testFindUserIdByPassengerIdAndFindByUserId() throws Exception {
        int userId = insertUser();
        PassengerBean passenger = new PassengerBean();
        passenger.setUserId(userId);
        passengerDAO.create(passenger);
        List<PassengerBean> all = passengerDAO.findAll();
        int passengerId = all.get(0).getId();
        assertEquals(userId, passengerDAO.findUserIdByPassengerId(passengerId));
        assertEquals(passengerId, passengerDAO.findByUserId(userId));
    }

    @Test
    void testCountAll() throws Exception {
        assertEquals(0, passengerDAO.countAll());
        int userId = insertUser();
        PassengerBean passenger = new PassengerBean();
        passenger.setUserId(userId);
        passengerDAO.create(passenger);
        assertEquals(1, passengerDAO.countAll());
    }

    @Test
    void testDeleteByUserId() throws Exception {
        int userId = insertUser();
        PassengerBean passenger = new PassengerBean();
        passenger.setUserId(userId);
        passengerDAO.create(passenger);
        assertTrue(passengerDAO.deleteByUserId(userId));
        assertEquals(0, passengerDAO.countAll());
    }
}
