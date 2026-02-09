package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TripDetailsPassengerServletIT extends BaseServletTest {

    @Test
    void tripDetails_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        TripDetailsPassengerServlet servlet = new TripDetailsPassengerServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
