package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteCarServletIT extends BaseServletTest {

    @Test
    void deleteCar_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.CAR_ID));

        DeleteCarServlet servlet = new DeleteCarServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?carDeleted=1");
    }
}
