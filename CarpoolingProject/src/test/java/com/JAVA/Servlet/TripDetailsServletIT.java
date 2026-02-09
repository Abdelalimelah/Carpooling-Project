package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TripDetailsServletIT extends BaseServletTest {

    @Test
    void tripDetails_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        TripDetailsServlet servlet = new TripDetailsServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
