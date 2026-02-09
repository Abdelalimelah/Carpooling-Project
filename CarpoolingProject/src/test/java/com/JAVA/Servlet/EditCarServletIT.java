package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EditCarServletIT extends BaseServletTest {

    @Test
    void doGet_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.CAR_ID));

        EditCarServlet servlet = new EditCarServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_valid_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.CAR_ID));
        when(ctx.req.getParameter("brand")).thenReturn("Peugeot");
        when(ctx.req.getParameter("model")).thenReturn("208");
        when(ctx.req.getParameter("plateNumber")).thenReturn("PEU-208");

        EditCarServlet servlet = new EditCarServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?carUpdated=1");
    }
}
