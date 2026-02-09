package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AddCarServletIT extends BaseServletTest {

    @Test
    void doGet_forwardsToForm() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        AddCarServlet servlet = new AddCarServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_valid_createsCar() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("brand")).thenReturn("Renault");
        when(ctx.req.getParameter("model")).thenReturn("Clio");
        when(ctx.req.getParameter("plateNumber")).thenReturn("CAR-1");

        AddCarServlet servlet = new AddCarServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?carAdded=1");
    }
}
