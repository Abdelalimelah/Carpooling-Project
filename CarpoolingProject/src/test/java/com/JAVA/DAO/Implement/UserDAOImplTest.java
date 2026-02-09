package com.JAVA.DAO.Implement;

import com.JAVA.Beans.UserBean;
import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest extends BaseDaoTest {

    @Test
    void registerAndFindAndUpdateAndDelete() throws Exception {
        try (Connection conn = TestDb.open()) {
            UserDAOImpl dao = new UserDAOImpl(conn);

            UserBean u = new UserBean();
            u.setEmail("new@carpool.com");
            u.setPasswordHash("hash");
            u.setFirstName("New");
            u.setLastName("User");
            u.setPhoneNumber("999");
            u.setRole("PASSENGER");

            assertTrue(dao.register(u));
            assertTrue(u.getId() > 0);

            assertTrue(dao.emailExists("new@carpool.com"));

            UserBean byEmail = dao.findByEmail("new@carpool.com");
            assertNotNull(byEmail);
            assertEquals("New", byEmail.getFirstName());

            UserBean byId = dao.findById(u.getId());
            assertNotNull(byId);
            assertEquals("new@carpool.com", byId.getEmail());

            byId.setFirstName("Updated");
            assertTrue(dao.update(byId));

            assertTrue(dao.updatePassword(byId.getId(), "newHash"));
            UserBean updated = dao.findByEmail("new@carpool.com");
            assertEquals("newHash", updated.getPasswordHash());

            int total = dao.countAll();
            assertTrue(total >= 1);

            assertFalse(dao.findPaged(0, 2).isEmpty());

            assertTrue(dao.deleteById(byId.getId()));
        }
    }
}
