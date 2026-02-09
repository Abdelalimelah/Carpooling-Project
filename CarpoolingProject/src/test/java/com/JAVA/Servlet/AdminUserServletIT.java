package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminUserServletIT extends BaseServletTest {

    @Test
    void editUser_forwards() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("edit");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.DRIVER_USER_ID));

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void updateUser_roleChange_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("update");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.PASSENGER_USER_ID));
        when(ctx.req.getParameter("email")).thenReturn("pass@carpool.com");
        when(ctx.req.getParameter("firstName")).thenReturn("Passenger");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("222");
        when(ctx.req.getParameter("role")).thenReturn("DRIVER");
        when(ctx.req.getParameter("licenceNumber")).thenReturn("LIC-NEW");

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
