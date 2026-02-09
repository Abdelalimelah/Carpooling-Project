package com.JAVA.DAO.Implement;

import com.JAVA.Beans.DriverBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class DriverDAOImplTest {
    private static Connection connection;
    private DriverDAOImpl driverDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_driver;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Driver (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    licence_number VARCHAR(100)
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
        driverDAO = new DriverDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Driver");
        }
    }

    @Test
    void testCreateAndFindById() throws Exception {
        DriverBean driver = new DriverBean();
        driver.setUserId(1);
        driver.setLicenceNumber("LIC123");
        assertTrue(driverDAO.create(driver));
        DriverBean found = driverDAO.findById(driver.getId());
        assertNotNull(found);
        assertEquals("LIC123", found.getLicenceNumber());
    }

    @Test
    void testGetDriverIdByUserId() throws Exception {
        DriverBean driver = new DriverBean();
        driver.setUserId(2);
        driver.setLicenceNumber("LIC456");
        driverDAO.create(driver);
        int driverId = driverDAO.getDriverIdByUserId(2);
        assertEquals(driver.getId(), driverId);
    }

    @Test
    void testFindUserIdByDriverId() throws Exception {
        DriverBean driver = new DriverBean();
        driver.setUserId(3);
        driver.setLicenceNumber("LIC789");
        driverDAO.create(driver);
        int userId = driverDAO.findUserIdByDriverId(driver.getId());
        assertEquals(3, userId);
    }

    @Test
    void testCountAll() throws Exception {
        assertEquals(0, driverDAO.countAll());
        DriverBean driver = new DriverBean();
        driver.setUserId(4);
        driver.setLicenceNumber("LIC000");
        driverDAO.create(driver);
        assertEquals(1, driverDAO.countAll());
    }

    @Test
    void testDeleteByUserId() throws Exception {
        DriverBean driver = new DriverBean();
        driver.setUserId(5);
        driver.setLicenceNumber("LICDEL");
        driverDAO.create(driver);
        assertTrue(driverDAO.deleteByUserId(5));
        assertEquals(0, driverDAO.countAll());
    }

    @Test
    void testUpdate() throws Exception {
        DriverBean driver = new DriverBean();
        driver.setUserId(6);
        driver.setLicenceNumber("LICOLD");
        driverDAO.create(driver);
        driver.setLicenceNumber("LICNEW");
        assertTrue(driverDAO.update(driver));
        DriverBean updated = driverDAO.findById(driver.getId());
        assertEquals("LICNEW", updated.getLicenceNumber());
    }
}
