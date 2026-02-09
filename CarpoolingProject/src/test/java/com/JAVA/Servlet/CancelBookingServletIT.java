package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CancelBookingServletIT extends BaseServletTest {

    @Test
    void cancelBooking_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.BOOKING_CONFIRMED_ID));
        when(ctx.req.getParameter("tripId")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        CancelBookingServlet servlet = new CancelBookingServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
