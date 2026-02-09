package com.JAVA.DAO.Implement;

import com.JAVA.Beans.TripBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TripDAOImplTest {
    private static Connection connection;
    private TripDAOImpl tripDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_trip;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
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
                );
                CREATE TABLE Booking (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    trip_id INT,
                    passenger_id INT,
                    status VARCHAR(50),
                    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
        tripDAO = new TripDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Booking");
            stmt.execute("DELETE FROM Trip");
        }
    }

    private TripBean createTrip(String dep, String arr, LocalDateTime date, BigDecimal price, int seats, int driverId, int carId) throws DAOException {
        TripBean trip = new TripBean();
        trip.setDepartureCity(dep);
        trip.setArrivalCity(arr);
        trip.setDateTime(date);
        trip.setPrice(price);
        trip.setSeatsAvailable(seats);
        trip.setDriverId(driverId);
        trip.setCarId(carId);
        assertTrue(tripDAO.create(trip));
        return trip;
    }

    @Test
    void testCreateAndFindById() throws Exception {
        TripBean trip = createTrip("Paris", "Lyon", LocalDateTime.now().plusDays(1), new BigDecimal("25.00"), 3, 1, 1);
        TripBean found = tripDAO.findById(trip.getId());
        assertNotNull(found);
        assertEquals("Paris", found.getDepartureCity());
    }

    @Test
    void testFindByDriverId() throws Exception {
        TripBean trip = createTrip("Paris", "Marseille", LocalDateTime.now().plusDays(2), new BigDecimal("30.00"), 2, 2, 2);
        List<TripBean> trips = tripDAO.findByDriverId(2);
        assertFalse(trips.isEmpty());
        assertEquals(2, trips.get(0).getDriverId());
    }

    @Test
    void testUpdate() throws Exception {
        TripBean trip = createTrip("Lille", "Nice", LocalDateTime.now().plusDays(3), new BigDecimal("40.00"), 4, 3, 3);
        trip.setArrivalCity("Bordeaux");
        assertTrue(tripDAO.update(trip));
        TripBean updated = tripDAO.findById(trip.getId());
        assertEquals("Bordeaux", updated.getArrivalCity());
    }

    @Test
    void testDelete() throws Exception {
        TripBean trip = createTrip("Dijon", "Toulouse", LocalDateTime.now().plusDays(4), new BigDecimal("35.00"), 5, 4, 4);
        assertTrue(tripDAO.delete(trip.getId()));
        assertNull(tripDAO.findById(trip.getId()));
    }

    @Test
    void testFindAvailableTripsForPassenger() throws Exception {
        TripBean trip = createTrip("Paris", "Lyon", LocalDateTime.now().plusDays(1), new BigDecimal("20.00"), 3, 5, 5);
        List<TripBean> trips = tripDAO.findAvailableTripsForPassenger(1, "Paris", "Lyon", null);
        assertFalse(trips.isEmpty());
    }

    @Test
    void testCountAllAndCountFiltered() throws Exception {
        createTrip("Paris", "Lyon", LocalDateTime.now().plusDays(1), new BigDecimal("20.00"), 3, 6, 6);
        assertEquals(1, tripDAO.countAll());
        assertTrue(tripDAO.countFiltered(null, null) >= 1);
    }

    @Test
    void testFindPagedFiltered() throws Exception {
        for (int i = 0; i < 5; i++) {
            createTrip("CityA", "CityB", LocalDateTime.now().plusDays(i + 1), new BigDecimal("10.00"), 2, 7, 7);
        }
        List<TripBean> list = tripDAO.findPagedFiltered(null, null, 0, 3);
        assertEquals(3, list.size());
    }

    @Test
    void testFindDerivedStatusForTripIds() throws Exception {
        TripBean trip = createTrip("Paris", "Lyon", LocalDateTime.now().plusDays(1), new BigDecimal("20.00"), 3, 8, 8);
        Set<Integer> ids = new HashSet<>();
        ids.add(trip.getId());
        Map<Integer, String> statusMap = tripDAO.findDerivedStatusForTripIds(ids);
        assertTrue(statusMap.containsKey(trip.getId()));
    }
}
