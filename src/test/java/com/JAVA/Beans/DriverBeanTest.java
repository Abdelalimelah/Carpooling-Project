package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DriverBeanTest {
    @Test
    void testDriverBeanGettersAndSetters() {
        DriverBean bean = new DriverBean();
        bean.setId(1);
        bean.setUserId(3);
        bean.setLicenceNumber("ABC123");
        assertEquals(1, bean.getId());
        assertEquals(3, bean.getUserId());
        assertEquals("ABC123", bean.getLicenceNumber());
    }
}
