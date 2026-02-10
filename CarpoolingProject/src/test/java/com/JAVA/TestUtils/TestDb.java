package com.JAVA.TestUtils;

import com.JAVA.Utils.PasswordUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public final class TestDb {
    public static final String JDBC_URL = "jdbc:h2:mem:carpooling;MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final String USER = "sa";
    public static final String PASS = "";

    public static final int ADMIN_USER_ID = 1;
    public static final int DRIVER_USER_ID = 2;
    public static final int PASSENGER_USER_ID = 3;
    public static final int PASSENGER2_USER_ID = 4;

    public static final int DRIVER_ID = 1;
    public static final int PASSENGER_ID = 1;
    public static final int PASSENGER2_ID = 2;
    public static final int CAR_ID = 1;

    public static final int TRIP_FUTURE_ID = 1;
    public static final int TRIP_PAST_ID = 2;

    public static final int BOOKING_CONFIRMED_ID = 1;
    public static final int BOOKING_COMPLETED_ID = 2;
    public static final int BOOKING_CANCELLED_ID = 3;

    public static final int FEEDBACK_ID = 1;
    public static final int NOTIF_UNREAD_ID = 1;
    public static final int NOTIF_READ_ID = 2;

    private TestDb() {}

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    public static void reset() {
        try (Connection conn = open();
             Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS Booking");
            st.execute("DROP TABLE IF EXISTS Trip");
            st.execute("DROP TABLE IF EXISTS Car");
            st.execute("DROP TABLE IF EXISTS Driver");
            st.execute("DROP TABLE IF EXISTS Passenger");
            st.execute("DROP TABLE IF EXISTS FeedBack");
            st.execute("DROP TABLE IF EXISTS notification");
            st.execute("DROP TABLE IF EXISTS User");

            st.execute("""
                CREATE TABLE User (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  email VARCHAR(255),
                  password_hash VARCHAR(255),
                  first_name VARCHAR(100),
                  last_name VARCHAR(100),
                  phone_number VARCHAR(50),
                  role VARCHAR(20),
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            st.execute("""
                CREATE TABLE Driver (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  user_id INT,
                  licence_number VARCHAR(100)
                )
            """);

            st.execute("""
                CREATE TABLE Passenger (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  user_id INT
                )
            """);

            st.execute("""
                CREATE TABLE Car (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  brand VARCHAR(100),
                  model VARCHAR(100),
                  plate_number VARCHAR(50),
                  driver_id INT
                )
            """);

            st.execute("""
                CREATE TABLE Trip (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  departure_city VARCHAR(100),
                  arrival_city VARCHAR(100),
                  date_time TIMESTAMP,
                  price DECIMAL(10,2),
                  seats_available INT,
                  driver_id INT,
                  car_id INT,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            st.execute("""
                CREATE TABLE Booking (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  trip_id INT,
                  passenger_id INT,
                  status VARCHAR(20),
                  booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            st.execute("""
                CREATE TABLE FeedBack (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  rating INT,
                  comment VARCHAR(500),
                  date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  author_id INT,
                  target_id INT,
                  feedback_type VARCHAR(20),
                  trip_id INT
                )
            """);

            st.execute("""
                CREATE TABLE notification (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  user_id INT,
                  type VARCHAR(50),
                  message VARCHAR(500),
                  is_read BOOLEAN DEFAULT FALSE,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  metadata VARCHAR(500)
                )
            """);

            seed(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void seed(Connection conn) throws SQLException {
        insertUser(conn, ADMIN_USER_ID, "admin@carpool.com", "adminpass", "Admin", "User", "000", "ADMIN");
        insertUser(conn, DRIVER_USER_ID, "driver@carpool.com", "driverpass", "Driver", "One", "111", "DRIVER");
        insertUser(conn, PASSENGER_USER_ID, "pass@carpool.com", "passpass", "Passenger", "One", "222", "PASSENGER");
        insertUser(conn, PASSENGER2_USER_ID, "pass2@carpool.com", "pass2pass", "Passenger", "Two", "333", "PASSENGER");

        insertDriver(conn, DRIVER_ID, DRIVER_USER_ID, "DRV-123");
        insertPassenger(conn, PASSENGER_ID, PASSENGER_USER_ID);
        insertPassenger(conn, PASSENGER2_ID, PASSENGER2_USER_ID);

        insertCar(conn, CAR_ID, "Toyota", "Corolla", "ABC-123", DRIVER_ID);

        LocalDateTime now = LocalDateTime.now();
        insertTrip(conn, TRIP_FUTURE_ID, "Paris", "Lyon",
                Timestamp.valueOf(now.plusDays(1)), new BigDecimal("10.00"), 3, DRIVER_ID, CAR_ID);
        insertTrip(conn, TRIP_PAST_ID, "Lille", "Marseille",
                Timestamp.valueOf(now.minusDays(2)), new BigDecimal("20.00"), 2, DRIVER_ID, CAR_ID);

        insertBooking(conn, BOOKING_CONFIRMED_ID, TRIP_FUTURE_ID, PASSENGER_ID, "CONFIRMED");
        insertBooking(conn, BOOKING_COMPLETED_ID, TRIP_PAST_ID, PASSENGER_ID, "COMPLETED");
        insertBooking(conn, BOOKING_CANCELLED_ID, TRIP_FUTURE_ID, PASSENGER2_ID, "CANCELLED");

        insertFeedback(conn, FEEDBACK_ID, 5, "Great trip", PASSENGER_USER_ID, DRIVER_USER_ID, "DRIVER", TRIP_PAST_ID);

        insertNotification(conn, NOTIF_UNREAD_ID, DRIVER_USER_ID, "NEW_BOOKING", "New booking", false, "{\"url\":\"/x\"}");
        insertNotification(conn, NOTIF_READ_ID, PASSENGER_USER_ID, "BOOKING_CONFIRMED", "Confirmed", true, null);

        try (Statement st = conn.createStatement()) {
            st.execute("ALTER TABLE User ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE Driver ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE Passenger ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE Car ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE Trip ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE Booking ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE FeedBack ALTER COLUMN id RESTART WITH 100");
            st.execute("ALTER TABLE notification ALTER COLUMN id RESTART WITH 100");
        }
    }

    public static void insertUser(Connection conn, int id, String email, String rawPassword,
                                  String firstName, String lastName, String phone, String role) throws SQLException {
        String hash = PasswordUtil.hashPasswordWithSalt(rawPassword);
        try (PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO User (id, email, password_hash, first_name, last_name, phone_number, role, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
        """)) {
            ps.setInt(1, id);
            ps.setString(2, email);
            ps.setString(3, hash);
            ps.setString(4, firstName);
            ps.setString(5, lastName);
            ps.setString(6, phone);
            ps.setString(7, role);
            ps.executeUpdate();
        }
    }

    public static void insertDriver(Connection conn, int id, int userId, String licence) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Driver (id, user_id, licence_number) VALUES (?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.setString(3, licence);
            ps.executeUpdate();
        }
    }

    public static void insertPassenger(Connection conn, int id, int userId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Passenger (id, user_id) VALUES (?, ?)")) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public static void insertCar(Connection conn, int id, String brand, String model,
                                 String plate, int driverId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Car (id, brand, model, plate_number, driver_id) VALUES (?, ?, ?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, brand);
            ps.setString(3, model);
            ps.setString(4, plate);
            ps.setInt(5, driverId);
            ps.executeUpdate();
        }
    }

    public static void insertTrip(Connection conn, int id, String dep, String arr, Timestamp date,
                                  BigDecimal price, int seats, int driverId, int carId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO Trip (id, departure_city, arrival_city, date_time, price, seats_available, driver_id, car_id, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
        """)) {
            ps.setInt(1, id);
            ps.setString(2, dep);
            ps.setString(3, arr);
            ps.setTimestamp(4, date);
            ps.setBigDecimal(5, price);
            ps.setInt(6, seats);
            ps.setInt(7, driverId);
            ps.setInt(8, carId);
            ps.executeUpdate();
        }
    }

    public static void insertBooking(Connection conn, int id, int tripId, int passengerId, String status) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO Booking (id, trip_id, passenger_id, status, booking_date)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
        """)) {
            ps.setInt(1, id);
            ps.setInt(2, tripId);
            ps.setInt(3, passengerId);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public static void insertFeedback(Connection conn, int id, int rating, String comment,
                                      int authorId, int targetId, String type, int tripId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO FeedBack (id, rating, comment, date_time, author_id, target_id, feedback_type, trip_id)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)
        """)) {
            ps.setInt(1, id);
            ps.setInt(2, rating);
            ps.setString(3, comment);
            ps.setInt(4, authorId);
            ps.setInt(5, targetId);
            ps.setString(6, type);
            ps.setInt(7, tripId);
            ps.executeUpdate();
        }
    }

    public static void insertNotification(Connection conn, int id, int userId, String type,
                                          String message, boolean read, String metadata) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO notification (id, user_id, type, message, is_read, created_at, metadata)
            VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
        """)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.setString(3, type);
            ps.setString(4, message);
            ps.setBoolean(5, read);
            if (metadata == null) {
                ps.setNull(6, Types.VARCHAR);
            } else {
                ps.setString(6, metadata);
            }
            ps.executeUpdate();
        }
    }
}
