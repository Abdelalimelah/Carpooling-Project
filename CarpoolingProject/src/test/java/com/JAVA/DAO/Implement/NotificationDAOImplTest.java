package com.JAVA.DAO.Implement;

import com.JAVA.Beans.NotificationBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDAOImplTest extends BaseDaoTest {

    @Test
    void createListAndMarkRead() throws Exception {
        try (Connection conn = TestDb.open()) {
            NotificationDAOImpl dao = new NotificationDAOImpl(conn);

            NotificationBean n = new NotificationBean();
            n.setUserId(TestDb.DRIVER_USER_ID);
            n.setType("TEST");
            n.setMessage("Hello");
            n.setMetadata("{\"x\":1}");

            int id = dao.create(n);
            assertTrue(id > 0);

            assertTrue(dao.countUnread(TestDb.DRIVER_USER_ID) >= 1);
            assertFalse(dao.getUnreadByUser(TestDb.DRIVER_USER_ID, 10, 0).isEmpty());
            assertFalse(dao.listRecent(TestDb.DRIVER_USER_ID, 10).isEmpty());

            dao.markAsReadById(TestDb.DRIVER_USER_ID, TestDb.NOTIF_UNREAD_ID);
            dao.markAllAsRead(TestDb.DRIVER_USER_ID);
            assertEquals(0, dao.countUnread(TestDb.DRIVER_USER_ID));
        }
    }
}
