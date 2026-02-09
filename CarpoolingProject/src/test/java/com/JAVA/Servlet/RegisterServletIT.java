package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RegisterServletIT extends BaseServletTest {

    @Test
    void registerPassenger_redirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("email")).thenReturn("newpass@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret1");
        when(ctx.req.getParameter("firstName")).thenReturn("New");
        when(ctx.req.getParameter("lastName")).thenReturn("Pass");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("444");

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("login?registered=1");
    }
}
