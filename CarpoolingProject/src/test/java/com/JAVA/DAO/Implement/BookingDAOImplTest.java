package com.JAVA.DAO.Implement;

import com.JAVA.Beans.BookingBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingDAOImplTest extends BaseDaoTest {

    @Test
    void createFindUpdateCountsAndDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            BookingDAOImpl dao = new BookingDAOImpl(conn);

            BookingBean b = new BookingBean();
            b.setTripId(TestDb.TRIP_FUTURE_ID);
            b.setPassengerId(TestDb.PASSENGER2_ID);
            b.setStatus("PENDING");
            assertTrue(dao.create(b));

            List<BookingBean> byTrip = dao.findByTripId(TestDb.TRIP_FUTURE_ID);
            assertFalse(byTrip.isEmpty());

            assertTrue(dao.updateStatus(TestDb.BOOKING_CONFIRMED_ID, "CONFIRMED"));
            assertTrue(dao.countConfirmedBookingsByTripId(TestDb.TRIP_FUTURE_ID) >= 1);

            assertFalse(dao.findByPassengerId(TestDb.PASSENGER_ID).isEmpty());

            BigDecimal earnings = dao.calculateTotalEarningsByDriver(TestDb.DRIVER_ID);
            assertEquals(0, earnings.compareTo(new BigDecimal("20.00")));

            dao.markTripAsCompleted(TestDb.TRIP_FUTURE_ID);
            assertTrue(dao.tripIsCompleted(TestDb.TRIP_FUTURE_ID));

            assertNotNull(dao.findById(TestDb.BOOKING_CONFIRMED_ID));

            assertTrue(dao.countAll() >= 1);
            assertTrue(dao.countByStatus("CANCELLED") >= 1);
            assertEquals(0, dao.sumCompletedEarnings().compareTo(new BigDecimal("20.00")));

            assertTrue(dao.countFiltered(TestDb.TRIP_FUTURE_ID, null, null) >= 1);
            assertFalse(dao.findPagedFiltered(null, null, null, 0, 10).isEmpty());

            assertTrue(dao.deleteById(TestDb.BOOKING_CANCELLED_ID));
        }
    }
}
