package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CancelTripPassengerServletIT extends BaseServletTest {

    @Test
    void cancelByPassenger_redirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.BOOKING_CONFIRMED_ID));

        CancelTripPassengerServlet servlet = new CancelTripPassengerServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/passenger/dashboard?bookingCancelled=1");
    }
}
