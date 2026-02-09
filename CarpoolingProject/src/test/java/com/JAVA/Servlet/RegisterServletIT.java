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

    @Test
    void missingFields_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("email")).thenReturn("");
        when(ctx.req.getParameter("password")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret1");

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void passwordMismatch_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("email")).thenReturn("x@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret2");
        when(ctx.req.getParameter("firstName")).thenReturn("X");
        when(ctx.req.getParameter("lastName")).thenReturn("Y");

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void emailAlreadyExists_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("email")).thenReturn("pass@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret1");
        when(ctx.req.getParameter("firstName")).thenReturn("P");
        when(ctx.req.getParameter("lastName")).thenReturn("One");

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void driverMissingInfo_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("role")).thenReturn("DRIVER");
        when(ctx.req.getParameter("email")).thenReturn("newdriver@carpool.com");
        when(ctx.req.getParameter("password")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret1");
        when(ctx.req.getParameter("firstName")).thenReturn("D");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("555");
        // Missing licence/car info triggers exception
        when(ctx.req.getParameter("licenceNumber")).thenReturn("");

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
