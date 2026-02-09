package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerBeanTest {
    @Test
    void testPassengerBeanGettersAndSetters() {
        PassengerBean bean = new PassengerBean();
        bean.setId(1);
        bean.setUserId(2);
        assertEquals(1, bean.getId());
        assertEquals(2, bean.getUserId());
    }
}
