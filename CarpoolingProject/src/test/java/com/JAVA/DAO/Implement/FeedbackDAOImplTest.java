package com.JAVA.DAO.Implement;

import com.JAVA.Beans.FeedbackBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackDAOImplTest {
    private static Connection connection;
    private FeedbackDAOImpl feedbackDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_feedback;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE FeedBack (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    rating INT,
                    comment VARCHAR(255),
                    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    author_id INT,
                    target_id INT,
                    feedback_type VARCHAR(50),
                    trip_id INT
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
        feedbackDAO = new FeedbackDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM FeedBack");
        }
    }

    @Test
    void testCreateAndFindByTargetAndType() throws Exception {
        FeedbackBean fb = new FeedbackBean();
        fb.setRating(5);
        fb.setComment("Excellent");
        fb.setAuthorId(1);
        fb.setTargetId(2);
        fb.setFeedbackType("DRIVER");
        fb.setTripId(10);
        assertTrue(feedbackDAO.create(fb));
        List<FeedbackBean> list = feedbackDAO.findByTargetAndType(2, "DRIVER");
        assertFalse(list.isEmpty());
        assertEquals("Excellent", list.get(0).getComment());
    }

    @Test
    void testExistsByAuthorAndTargetAndTrip() throws Exception {
        FeedbackBean fb = new FeedbackBean();
        fb.setRating(4);
        fb.setComment("Good");
        fb.setAuthorId(3);
        fb.setTargetId(4);
        fb.setFeedbackType("PASSENGER");
        fb.setTripId(20);
        feedbackDAO.create(fb);
        assertTrue(feedbackDAO.existsByAuthorAndTargetAndTrip(3, 4, 20));
        assertFalse(feedbackDAO.existsByAuthorAndTargetAndTrip(99, 99, 99));
    }

    @Test
    void testGetAverageRatingForTarget() throws Exception {
        FeedbackBean fb1 = new FeedbackBean();
        fb1.setRating(3);
        fb1.setComment("Ok");
        fb1.setAuthorId(5);
        fb1.setTargetId(6);
        fb1.setFeedbackType("DRIVER");
        fb1.setTripId(30);
        feedbackDAO.create(fb1);
        FeedbackBean fb2 = new FeedbackBean();
        fb2.setRating(5);
        fb2.setComment("Great");
        fb2.setAuthorId(7);
        fb2.setTargetId(6);
        fb2.setFeedbackType("DRIVER");
        fb2.setTripId(31);
        feedbackDAO.create(fb2);
        double avg = feedbackDAO.getAverageRatingForTarget(6, "DRIVER");
        assertEquals(4.0, avg);
    }

    @Test
    void testCountAllAndCountFiltered() throws Exception {
        FeedbackBean fb = new FeedbackBean();
        fb.setRating(2);
        fb.setComment("Bad");
        fb.setAuthorId(8);
        fb.setTargetId(9);
        fb.setFeedbackType("PASSENGER");
        fb.setTripId(40);
        feedbackDAO.create(fb);
        assertEquals(1, feedbackDAO.countAll());
        assertEquals(1, feedbackDAO.countFiltered("PASSENGER", 8, 9, 2));
    }

    @Test
    void testFindPagedFiltered() throws Exception {
        for (int i = 0; i < 5; i++) {
            FeedbackBean fb = new FeedbackBean();
            fb.setRating(5);
            fb.setComment("Paged" + i);
            fb.setAuthorId(10);
            fb.setTargetId(11);
            fb.setFeedbackType("DRIVER");
            fb.setTripId(50 + i);
            feedbackDAO.create(fb);
        }
        List<FeedbackBean> list = feedbackDAO.findPagedFiltered("DRIVER", 10, 11, 5, 0, 3);
        assertEquals(3, list.size());
    }

    @Test
    void testDelete() throws Exception {
        FeedbackBean fb = new FeedbackBean();
        fb.setRating(1);
        fb.setComment("ToDelete");
        fb.setAuthorId(12);
        fb.setTargetId(13);
        fb.setFeedbackType("PASSENGER");
        fb.setTripId(60);
        feedbackDAO.create(fb);
        List<FeedbackBean> list = feedbackDAO.findByTargetAndType(13, "PASSENGER");
        assertFalse(list.isEmpty());
        int id = list.get(0).getId();
        assertTrue(feedbackDAO.delete(id));
        assertEquals(0, feedbackDAO.countAll());
    }
}
