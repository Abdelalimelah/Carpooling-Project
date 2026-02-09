package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MarkAllReadServletIT extends BaseServletTest {

    @Test
    void markAllRead_setsStatus() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));

        MarkAllReadServlet servlet = new MarkAllReadServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
