package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class UserBeanTest {
    @Test
    void testUserBeanGettersAndSetters() {
        UserBean bean = new UserBean();
        bean.setId(1);
        bean.setEmail("test@mail.com");
        bean.setPasswordHash("hash");
        bean.setFirstName("John");
        bean.setLastName("Doe");
        bean.setPhoneNumber("1234567890");
        bean.setRole("admin");
        LocalDateTime now = LocalDateTime.now();
        bean.setCreatedAt(now);
        bean.setUpdatedAt(now);
        assertEquals(1, bean.getId());
        assertEquals("test@mail.com", bean.getEmail());
        assertEquals("hash", bean.getPasswordHash());
        assertEquals("John", bean.getFirstName());
        assertEquals("Doe", bean.getLastName());
        assertEquals("1234567890", bean.getPhoneNumber());
        assertEquals("admin", bean.getRole());
        assertEquals(now, bean.getCreatedAt());
        assertEquals(now, bean.getUpdatedAt());
    }
}
