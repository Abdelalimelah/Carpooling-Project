package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ProfileServletIT extends BaseServletTest {

    @Test
    void doGet_edit_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getPathInfo()).thenReturn("/edit");

        ProfileServlet servlet = new ProfileServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_success_redirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("firstName")).thenReturn("P");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("999");
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("newPassword")).thenReturn("");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("");

        ProfileServlet servlet = new ProfileServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/profile?updated=1");
    }

    @Test
    void doPost_passwordMismatch_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("firstName")).thenReturn("P");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("999");
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("newPassword")).thenReturn("secret1");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("secret2");

        ProfileServlet servlet = new ProfileServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_shortPassword_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("firstName")).thenReturn("P");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("999");
        when(ctx.req.getParameter("role")).thenReturn("PASSENGER");
        when(ctx.req.getParameter("newPassword")).thenReturn("123");
        when(ctx.req.getParameter("confirmPassword")).thenReturn("123");

        ProfileServlet servlet = new ProfileServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_noUser_redirectsLogin() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.session.getAttribute("user")).thenReturn(null);
        ProfileServlet servlet = new ProfileServlet();
        servlet.doPost(ctx.req, ctx.resp);
        verify(ctx.resp).sendRedirect("/app/login");
    }
}
