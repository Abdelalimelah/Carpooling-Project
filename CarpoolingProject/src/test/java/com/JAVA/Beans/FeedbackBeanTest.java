package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackBeanTest {
    @Test
    void testFeedbackBeanGettersAndSetters() {
        FeedbackBean bean = new FeedbackBean();
        bean.setId(1);
        bean.setRating(5);
        bean.setComment("Excellent!");
        LocalDateTime now = LocalDateTime.now();
        bean.setDateTime(now);
        bean.setAuthorId(10);
        bean.setTargetId(20);
        bean.setFeedbackType("DRIVER");
        bean.setTripId(100);
        bean.setBookingId(200);
        assertEquals(1, bean.getId());
        assertEquals(5, bean.getRating());
        assertEquals("Excellent!", bean.getComment());
        assertEquals(now, bean.getDateTime());
        assertEquals(10, bean.getAuthorId());
        assertEquals(20, bean.getTargetId());
        assertEquals("DRIVER", bean.getFeedbackType());
        assertEquals(100, bean.getTripId());
        assertEquals(200, bean.getBookingId());
    }
}
