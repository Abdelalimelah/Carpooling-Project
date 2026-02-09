package com.JAVA.DAO.Implement;

import com.JAVA.Beans.PassengerBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PassengerDAOImplTest extends BaseDaoTest {

    @Test
    void createFindAndDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            TestDb.insertUser(conn, 11, "p11@carpool.com", "pwd", "P", "Eleven", "000", "PASSENGER");

            PassengerDAOImpl dao = new PassengerDAOImpl(conn);
            PassengerBean p = new PassengerBean();
            p.setUserId(11);
            assertTrue(dao.create(p));

            int pid = dao.findByUserId(11);
            int uid = dao.findUserIdByPassengerId(pid);
            assertEquals(11, uid);

            assertNotNull(dao.findById(pid));
            assertFalse(dao.findAll().isEmpty());
            assertTrue(dao.countAll() >= 1);

            assertTrue(dao.deleteByUserId(11));
        }
    }
}
