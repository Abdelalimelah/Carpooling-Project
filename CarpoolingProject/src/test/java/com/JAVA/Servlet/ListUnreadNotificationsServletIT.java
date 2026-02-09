package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ListUnreadNotificationsServletIT extends BaseServletTest {

    @Test
    void returnsJson() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("limit")).thenReturn("10");
        when(ctx.req.getParameter("offset")).thenReturn("0");

        StringWriter sw = attachWriter(ctx.resp);

        ListUnreadNotificationsServlet servlet = new ListUnreadNotificationsServlet();
        servlet.doGet(ctx.req, ctx.resp);

        assertTrue(sw.toString().contains("unreadCount"));
    }
}
