package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TripBeanTest {
    @Test
    void testTripBeanGettersAndSetters() {
        TripBean bean = new TripBean();
        bean.setId(1);
        bean.setDepartureCity("Paris");
        bean.setArrivalCity("Lyon");
        LocalDateTime now = LocalDateTime.now();
        bean.setDateTime(now);
        bean.setPrice(new BigDecimal("25.50"));
        bean.setSeatsAvailable(3);
        bean.setDriverId(10);
        bean.setCarId(20);
        bean.setCreatedAt(now);
        assertEquals(1, bean.getId());
        assertEquals("Paris", bean.getDepartureCity());
        assertEquals("Lyon", bean.getArrivalCity());
        assertEquals(now, bean.getDateTime());
        assertEquals(new BigDecimal("25.50"), bean.getPrice());
        assertEquals(3, bean.getSeatsAvailable());
        assertEquals(10, bean.getDriverId());
        assertEquals(20, bean.getCarId());
        assertEquals(now, bean.getCreatedAt());
    }
}
