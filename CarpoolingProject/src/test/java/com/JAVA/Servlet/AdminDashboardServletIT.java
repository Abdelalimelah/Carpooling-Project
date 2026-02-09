package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminDashboardServletIT extends BaseServletTest {

    @Test
    void loadsDashboard() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        AdminDashboardServlet servlet = new AdminDashboardServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
