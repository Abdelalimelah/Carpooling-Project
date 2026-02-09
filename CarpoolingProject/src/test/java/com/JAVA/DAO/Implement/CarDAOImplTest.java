package com.JAVA.DAO.Implement;

import com.JAVA.Beans.CarBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class CarDAOImplTest extends BaseDaoTest {

    @Test
    void createFindUpdateDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            CarDAOImpl dao = new CarDAOImpl(conn);
            CarBean c = new CarBean();
            c.setDriverId(TestDb.DRIVER_ID);
            c.setBrand("Ford");
            c.setModel("Focus");
            c.setPlateNumber("ZZZ-999");

            assertTrue(dao.create(c));

            assertFalse(dao.findByDriverId(TestDb.DRIVER_ID).isEmpty());

            CarBean found = dao.findById(TestDb.CAR_ID);
            assertNotNull(found);

            found.setBrand("Toyota");
            assertTrue(dao.update(found));

            assertTrue(dao.delete(TestDb.CAR_ID));
        }
    }
}
