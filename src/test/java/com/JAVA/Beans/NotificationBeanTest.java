package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificationBeanTest {
    @Test
    void testNotificationBeanGettersAndSetters() {
        NotificationBean bean = new NotificationBean();
        bean.setId(1);
        bean.setUserId(2);
        bean.setType("INFO");
        bean.setMessage("Welcome!");
        bean.setRead(true);
        LocalDateTime now = LocalDateTime.now();
        bean.setCreatedAt(now);
        bean.setMetadata("meta");
        assertEquals(1, bean.getId());
        assertEquals(2, bean.getUserId());
        assertEquals("INFO", bean.getType());
        assertEquals("Welcome!", bean.getMessage());
        assertTrue(bean.isRead());
        assertEquals(now, bean.getCreatedAt());
        assertEquals("meta", bean.getMetadata());
    }
}
