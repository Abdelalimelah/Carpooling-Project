package com.JAVA.DAO.Implement;

import com.JAVA.Beans.CarBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CarDAOImplTest {
    private static Connection connection;
    private CarDAOImpl carDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb_car;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Car (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    brand VARCHAR(100),
                    model VARCHAR(100),
                    plate_number VARCHAR(50),
                    driver_id INT
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
        carDAO = new CarDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Car");
        }
    }

    @Test
    void testCreateAndFindById() throws Exception {
        CarBean car = new CarBean();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setPlateNumber("ABC123");
        car.setDriverId(1);
        assertTrue(carDAO.create(car));
        CarBean found = carDAO.findById(1);
        assertNotNull(found);
        assertEquals("Toyota", found.getBrand());
    }

    @Test
    void testFindByDriverId() throws Exception {
        CarBean car = new CarBean();
        car.setBrand("Renault");
        car.setModel("Clio");
        car.setPlateNumber("XYZ789");
        car.setDriverId(2);
        carDAO.create(car);
        List<CarBean> cars = carDAO.findByDriverId(2);
        assertFalse(cars.isEmpty());
        assertEquals(2, cars.get(0).getDriverId());
    }

    @Test
    void testUpdate() throws Exception {
        CarBean car = new CarBean();
        car.setBrand("Peugeot");
        car.setModel("208");
        car.setPlateNumber("PLT456");
        car.setDriverId(3);
        carDAO.create(car);
        car.setId(1);
        car.setModel("308");
        assertTrue(carDAO.update(car));
        CarBean updated = carDAO.findById(1);
        assertEquals("308", updated.getModel());
    }

    @Test
    void testDelete() throws Exception {
        CarBean car = new CarBean();
        car.setBrand("Citroen");
        car.setModel("C3");
        car.setPlateNumber("DEL123");
        car.setDriverId(4);
        carDAO.create(car);
        assertTrue(carDAO.delete(1));
        assertNull(carDAO.findById(1));
    }
}
