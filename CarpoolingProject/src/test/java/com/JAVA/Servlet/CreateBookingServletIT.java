package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CreateBookingServletIT extends BaseServletTest {

    @Test
    void createBooking_redirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("tripId")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        CreateBookingServlet servlet = new CreateBookingServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/passenger/dashboard?bookingCreated=1");
    }
}
