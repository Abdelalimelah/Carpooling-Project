package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CompleteTripServletIT extends BaseServletTest {

    @Test
    void completeTrip_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        CompleteTripServlet servlet = new CompleteTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
