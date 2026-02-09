package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginServletIT extends BaseServletTest {

    @Test
    void validLogin_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("email")).thenReturn("driver@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("driverpass");

        LoginServlet servlet = new LoginServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("driver/dashboard");
    }

    @Test
    void invalidLogin_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("email")).thenReturn("driver@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("wrong");

        LoginServlet servlet = new LoginServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
