package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteTripServletIT extends BaseServletTest {

    @Test
    void deleteTrip_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        DeleteTripServlet servlet = new DeleteTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?tripDeleted=1");
    }
}
