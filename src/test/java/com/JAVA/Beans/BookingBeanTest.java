package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingBeanTest {
    @Test
    void testBookingBeanGettersAndSetters() {
        BookingBean bean = new BookingBean();
        bean.setId(1);
        bean.setTripId(2);
        bean.setPassengerId(3);
        bean.setStatus("CONFIRMED");
        assertEquals(1, bean.getId());
        assertEquals(2, bean.getTripId());
        assertEquals(3, bean.getPassengerId());
        assertEquals("CONFIRMED", bean.getStatus());
    }
}
