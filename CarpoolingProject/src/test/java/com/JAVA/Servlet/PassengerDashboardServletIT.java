package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PassengerDashboardServletIT extends BaseServletTest {

    @Test
    void dashboard_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        PassengerDashboardServlet servlet = new PassengerDashboardServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
