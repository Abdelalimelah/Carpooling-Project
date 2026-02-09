package com.JAVA.DAO.Implement;

import com.JAVA.Beans.DriverBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DriverDAOImplTest extends BaseDaoTest {

    @Test
    void createFindUpdateDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            TestDb.insertUser(conn, 10, "d10@carpool.com", "pwd", "D", "Ten", "000", "DRIVER");

            DriverDAOImpl dao = new DriverDAOImpl(conn);
            DriverBean d = new DriverBean();
            d.setUserId(10);
            d.setLicenceNumber("LIC-10");

            assertTrue(dao.create(d));
            assertTrue(d.getId() > 0);

            int driverId = dao.getDriverIdByUserId(10);
            assertEquals(d.getId(), driverId);

            int userId = dao.findUserIdByDriverId(driverId);
            assertEquals(10, userId);

            DriverBean found = dao.findById(driverId);
            assertNotNull(found);
            assertEquals("LIC-10", found.getLicenceNumber());

            found.setLicenceNumber("LIC-10-NEW");
            assertTrue(dao.update(found));

            assertTrue(dao.deleteByUserId(10));
            assertTrue(dao.countAll() >= 0);
        }
    }
}
