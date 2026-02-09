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
    void editUser_notFound_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("edit");
        when(ctx.req.getParameter("id")).thenReturn("999");

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void editUser_invalidAction_sends404() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("list");

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendError(404);
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

    @Test
    void updateUser_sameRoleDriver_updatesLicence() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("update");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.DRIVER_USER_ID));
        when(ctx.req.getParameter("email")).thenReturn("driver@carpool.com");
        when(ctx.req.getParameter("firstName")).thenReturn("Driver");
        when(ctx.req.getParameter("lastName")).thenReturn("One");
        when(ctx.req.getParameter("phoneNumber")).thenReturn("111");
        when(ctx.req.getParameter("role")).thenReturn("DRIVER");
        when(ctx.req.getParameter("licenceNumber")).thenReturn("LIC-UPDATED");

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void deleteUser_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("delete");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.PASSENGER2_USER_ID));

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void unknownAction_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("unknown");

        AdminUserServlet servlet = new AdminUserServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
