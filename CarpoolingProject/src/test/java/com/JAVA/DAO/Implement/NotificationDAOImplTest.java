package com.JAVA.DAO.Implement;

import com.JAVA.Beans.NotificationBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NotificationDAOImplTest {
    private static Connection connection;
    private NotificationDAOImpl notificationDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_notification;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE notification (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    type VARCHAR(50),
                    message VARCHAR(255),
                    is_read BOOLEAN DEFAULT FALSE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    metadata VARCHAR(255)
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
        notificationDAO = new NotificationDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM notification");
        }
    }

    @Test
    void testCreateAndListRecent() throws Exception {
        NotificationBean notif = new NotificationBean();
        notif.setUserId(1);
        notif.setType("INFO");
        notif.setMessage("Welcome");
        notif.setMetadata(null);
        int id = notificationDAO.create(notif);
        assertTrue(id > 0);
        List<NotificationBean> list = notificationDAO.listRecent(1, 10);
        assertFalse(list.isEmpty());
        assertEquals("Welcome", list.get(0).getMessage());
    }

    @Test
    void testCountUnreadAndGetUnreadByUser() throws Exception {
        NotificationBean notif = new NotificationBean();
        notif.setUserId(2);
        notif.setType("ALERT");
        notif.setMessage("Check this");
        notif.setMetadata(null);
        notificationDAO.create(notif);
        assertEquals(1, notificationDAO.countUnread(2));
        List<NotificationBean> unread = notificationDAO.getUnreadByUser(2, 10, 0);
        assertEquals(1, unread.size());
    }

    @Test
    void testMarkAllAsReadAndMarkAsReadById() throws Exception {
        NotificationBean notif = new NotificationBean();
        notif.setUserId(3);
        notif.setType("INFO");
        notif.setMessage("To read");
        notif.setMetadata(null);
        int id = notificationDAO.create(notif);
        assertEquals(1, notificationDAO.countUnread(3));
        notificationDAO.markAsReadById(3, id);
        assertEquals(0, notificationDAO.countUnread(3));
        // Add another and test markAllAsRead
        NotificationBean notif2 = new NotificationBean();
        notif2.setUserId(3);
        notif2.setType("INFO");
        notif2.setMessage("Another");
        notif2.setMetadata(null);
        notificationDAO.create(notif2);
        assertEquals(1, notificationDAO.countUnread(3));
        notificationDAO.markAllAsRead(3);
        assertEquals(0, notificationDAO.countUnread(3));
    }
}
