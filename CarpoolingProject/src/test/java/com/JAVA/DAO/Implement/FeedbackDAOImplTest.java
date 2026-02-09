package com.JAVA.DAO.Implement;

import com.JAVA.Beans.FeedbackBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackDAOImplTest extends BaseDaoTest {

    @Test
    void createFindAndDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            FeedbackDAOImpl dao = new FeedbackDAOImpl(conn);

            assertTrue(dao.existsByAuthorAndTargetAndTrip(
                    TestDb.PASSENGER_USER_ID, TestDb.DRIVER_USER_ID, TestDb.TRIP_PAST_ID));

            double avg = dao.getAverageRatingForTarget(TestDb.DRIVER_USER_ID, "DRIVER");
            assertEquals(5.0, avg);

            assertFalse(dao.findByTargetAndType(TestDb.DRIVER_USER_ID, "DRIVER").isEmpty());

            FeedbackBean f = new FeedbackBean();
            f.setRating(4);
            f.setComment("Ok");
            f.setAuthorId(TestDb.PASSENGER2_USER_ID);
            f.setTargetId(TestDb.DRIVER_USER_ID);
            f.setFeedbackType("DRIVER");
            f.setTripId(TestDb.TRIP_FUTURE_ID);
            assertTrue(dao.create(f));

            assertTrue(dao.countAll() >= 1);
            assertTrue(dao.countFiltered("DRIVER", null, TestDb.DRIVER_USER_ID, 4) >= 1);
            assertFalse(dao.findPagedFiltered("DRIVER", null, TestDb.DRIVER_USER_ID, 1, 0, 10).isEmpty());

            assertTrue(dao.delete(TestDb.FEEDBACK_ID));
        }
    }
}
