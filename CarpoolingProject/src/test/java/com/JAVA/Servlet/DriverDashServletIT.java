package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DriverDashServletIT extends BaseServletTest {

    @Test
    void dashboard_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        DriverDashServlet servlet = new DriverDashServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
