package com.JAVA.DAO.Implement;

import com.JAVA.Beans.BookingBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookingDAOImplTest {
    private static Connection connection;
    private BookingDAOImpl bookingDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb2;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Booking (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    trip_id INT,
                    passenger_id INT,
                    status VARCHAR(50),
                    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                CREATE TABLE Trip (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    driver_id INT,
                    price DECIMAL(10,2)
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
        bookingDAO = new BookingDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Booking");
            stmt.execute("DELETE FROM Trip");
        }
    }

    @Test
    void testCreateAndFindById() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(1);
        booking.setPassengerId(2);
        booking.setStatus("CONFIRMED");
        assertTrue(bookingDAO.create(booking));
        // Find by id (assuming auto-increment starts at 1)
        BookingBean found = bookingDAO.findById(1);
        assertNotNull(found);
        assertEquals(1, found.getTripId());
        assertEquals(2, found.getPassengerId());
        assertEquals("CONFIRMED", found.getStatus());
    }

    @Test
    void testFindByTripId() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(10);
        booking.setPassengerId(20);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        List<BookingBean> bookings = bookingDAO.findByTripId(10);
        assertFalse(bookings.isEmpty());
        assertEquals(10, bookings.get(0).getTripId());
    }

    @Test
    void testUpdateStatus() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(2);
        booking.setPassengerId(3);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        assertTrue(bookingDAO.updateStatus(1, "CANCELLED"));
        BookingBean updated = bookingDAO.findById(1);
        assertEquals("CANCELLED", updated.getStatus());
    }

    @Test
    void testCountConfirmedBookingsByTripId() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(5);
        booking.setPassengerId(6);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        assertEquals(1, bookingDAO.countConfirmedBookingsByTripId(5));
    }

    @Test
    void testFindByPassengerId() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(7);
        booking.setPassengerId(8);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        List<BookingBean> bookings = bookingDAO.findByPassengerId(8);
        assertFalse(bookings.isEmpty());
        assertEquals(8, bookings.get(0).getPassengerId());
    }

    @Test
    void testCalculateTotalEarningsByDriver() throws Exception {
        // Insert a trip and a completed booking
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO Trip (id, driver_id, price) VALUES (1, 100, 50.00)");
        }
        BookingBean booking = new BookingBean();
        booking.setTripId(1);
        booking.setPassengerId(9);
        booking.setStatus("COMPLETED");
        bookingDAO.create(booking);
        BigDecimal earnings = bookingDAO.calculateTotalEarningsByDriver(100);
        assertEquals(new BigDecimal("50.00"), earnings);
    }

    @Test
    void testMarkTripAsCompletedAndTripIsCompleted() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(2);
        booking.setPassengerId(10);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        bookingDAO.markTripAsCompleted(2);
        assertTrue(bookingDAO.tripIsCompleted(2));
    }

    @Test
    void testCountAllAndCountByStatus() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(3);
        booking.setPassengerId(11);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        assertEquals(1, bookingDAO.countAll());
        assertEquals(1, bookingDAO.countByStatus("CONFIRMED"));
    }

    @Test
    void testSumCompletedEarnings() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO Trip (id, driver_id, price) VALUES (2, 101, 75.00)");
        }
        BookingBean booking = new BookingBean();
        booking.setTripId(2);
        booking.setPassengerId(12);
        booking.setStatus("COMPLETED");
        bookingDAO.create(booking);
        BigDecimal sum = bookingDAO.sumCompletedEarnings();
        assertEquals(new BigDecimal("75.00"), sum);
    }

    @Test
    void testCountFilteredAndFindPagedFiltered() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(4);
        booking.setPassengerId(13);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        int count = bookingDAO.countFiltered(4, 13, "CONFIRMED");
        assertEquals(1, count);
        List<BookingBean> list = bookingDAO.findPagedFiltered(4, 13, "CONFIRMED", 0, 10);
        assertEquals(1, list.size());
    }

    @Test
    void testDeleteById() throws Exception {
        BookingBean booking = new BookingBean();
        booking.setTripId(5);
        booking.setPassengerId(14);
        booking.setStatus("CONFIRMED");
        bookingDAO.create(booking);
        assertTrue(bookingDAO.deleteById(1));
        assertNull(bookingDAO.findById(1));
    }
}
