package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LogoutServletIT extends BaseServletTest {

    @Test
    void logout_invalidatesAndRedirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        LogoutServlet servlet = new LogoutServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.session).invalidate();
        verify(ctx.resp).sendRedirect("/app/login?logout=1");
    }
}
