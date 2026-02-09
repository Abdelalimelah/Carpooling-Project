package com.JAVA.DAO.Implement;

import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.Exception.DAOException;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private static Connection connection;
    private UserDAOImpl userDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE user (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    email VARCHAR(255) UNIQUE,
                    password_hash VARCHAR(255),
                    first_name VARCHAR(255),
                    last_name VARCHAR(255),
                    phone_number VARCHAR(255),
                    role VARCHAR(50),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    @AfterAll
    static void tearDownDatabase() throws Exception {
        connection.close();
    }

    @BeforeEach
    void setUp() throws Exception {
        userDAO = new UserDAOImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM user");
        }
    }

    @Test
    void testRegisterAndFindByEmail() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("test@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("1234567890");
        user.setRole("USER");
        assertTrue(userDAO.register(user));
        assertNotNull(user.getId());
        UserBean found = userDAO.findByEmail("test@mail.com");
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    void testEmailExists() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("exists@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setPhoneNumber("0987654321");
        user.setRole("USER");
        userDAO.register(user);
        assertTrue(userDAO.emailExists("exists@mail.com"));
        assertFalse(userDAO.emailExists("notfound@mail.com"));
    }

    @Test
    void testFindById() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("findid@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("Alice");
        user.setLastName("Wonder");
        user.setPhoneNumber("111222333");
        user.setRole("USER");
        userDAO.register(user);
        UserBean found = userDAO.findById(user.getId());
        assertNotNull(found);
        assertEquals("Alice", found.getFirstName());
    }

    @Test
    void testUpdate() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("update@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("Bob");
        user.setLastName("Builder");
        user.setPhoneNumber("444555666");
        user.setRole("USER");
        userDAO.register(user);
        user.setFirstName("Bobby");
        assertTrue(userDAO.update(user));
        UserBean updated = userDAO.findById(user.getId());
        assertEquals("Bobby", updated.getFirstName());
    }

    @Test
    void testUpdatePassword() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("pass@mail.com");
        user.setPasswordHash("oldhash");
        user.setFirstName("Pass");
        user.setLastName("Word");
        user.setPhoneNumber("777888999");
        user.setRole("USER");
        userDAO.register(user);
        assertTrue(userDAO.updatePassword(user.getId(), "newhash"));
        UserBean updated = userDAO.findById(user.getId());
        assertEquals("newhash", updated.getPasswordHash());
    }

    @Test
    void testCountAll() throws Exception {
        assertEquals(0, userDAO.countAll());
        UserBean user = new UserBean();
        user.setEmail("count@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("Count");
        user.setLastName("All");
        user.setPhoneNumber("000111222");
        user.setRole("USER");
        userDAO.register(user);
        assertEquals(1, userDAO.countAll());
    }

    @Test
    void testDeleteById() throws Exception {
        UserBean user = new UserBean();
        user.setEmail("delete@mail.com");
        user.setPasswordHash("hash");
        user.setFirstName("Del");
        user.setLastName("Ete");
        user.setPhoneNumber("333444555");
        user.setRole("USER");
        userDAO.register(user);
        assertTrue(userDAO.deleteById(user.getId()));
        assertNull(userDAO.findById(user.getId()));
    }

    @Test
    void testFindPaged() throws Exception {
        for (int i = 1; i <= 5; i++) {
            UserBean user = new UserBean();
            user.setEmail("paged" + i + "@mail.com");
            user.setPasswordHash("hash");
            user.setFirstName("User" + i);
            user.setLastName("Test");
            user.setPhoneNumber("00000000" + i);
            user.setRole("USER");
            userDAO.register(user);
        }
        List<UserBean> users = userDAO.findPaged(0, 3);
        assertEquals(3, users.size());
        users = userDAO.findPaged(3, 3);
        assertTrue(users.size() <= 3);
    }
}
