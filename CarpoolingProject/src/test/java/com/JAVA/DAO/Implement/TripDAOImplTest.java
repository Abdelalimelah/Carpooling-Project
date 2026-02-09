package com.JAVA.DAO.Implement;

import com.JAVA.Beans.TripBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TripDAOImplTest extends BaseDaoTest {

    @Test
    void createFindUpdateDeleteAndFilters() throws Exception {
        try (Connection conn = TestDb.open()) {
            TripDAOImpl dao = new TripDAOImpl(conn);

            TripBean t = new TripBean();
            t.setDepartureCity("Nice");
            t.setArrivalCity("Paris");
            t.setDateTime(LocalDateTime.now().plusDays(3));
            t.setPrice(new BigDecimal("15.00"));
            t.setSeatsAvailable(3);
            t.setDriverId(TestDb.DRIVER_ID);
            t.setCarId(TestDb.CAR_ID);

            assertTrue(dao.create(t));
            assertTrue(t.getId() > 0);

            assertNotNull(dao.findById(TestDb.TRIP_FUTURE_ID));
            assertFalse(dao.findByDriverId(TestDb.DRIVER_ID).isEmpty());

            TripBean update = dao.findById(TestDb.TRIP_FUTURE_ID);
            update.setDepartureCity("Paris");
            assertTrue(dao.update(update));

            // Available trips for passenger
            TestDb.insertTrip(conn, 99, "Lyon", "Nantes",
                    Timestamp.valueOf(LocalDateTime.now().plusDays(2)),
                    new BigDecimal("12.00"), 2, TestDb.DRIVER_ID, TestDb.CAR_ID);

            List<TripBean> avail = dao.findAvailableTripsForPassenger(
                    TestDb.PASSENGER_ID, null, null, null);
            assertTrue(avail.stream().anyMatch(tr -> tr.getId() == 99));

            assertTrue(dao.countAll() >= 1);
            assertTrue(dao.countFiltered("COMPLETED", null) >= 1);

            assertFalse(dao.findPagedFiltered("UPCOMING", null, 0, 10).isEmpty());

            Map<Integer, String> statuses = dao.findDerivedStatusForTripIds(
                    Set.of(TestDb.TRIP_FUTURE_ID, TestDb.TRIP_PAST_ID));
            assertEquals("UPCOMING", statuses.get(TestDb.TRIP_FUTURE_ID));
            assertEquals("COMPLETED", statuses.get(TestDb.TRIP_PAST_ID));

            assertTrue(dao.delete(t.getId()));
        }
    }
}
