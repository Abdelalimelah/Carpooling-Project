package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BrowseTripsServletIT extends BaseServletTest {

    @Test
    void browseTrips_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        BrowseTripsServlet servlet = new BrowseTripsServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void browseTrips_noSession_forwardsError() throws Exception {
        var ctx = ctxNoSession();
        BrowseTripsServlet servlet = new BrowseTripsServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
