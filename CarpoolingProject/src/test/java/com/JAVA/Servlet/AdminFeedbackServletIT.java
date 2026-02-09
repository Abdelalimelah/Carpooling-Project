package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminFeedbackServletIT extends BaseServletTest {

    @Test
    void deleteFeedback_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("delete");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.FEEDBACK_ID));

        AdminFeedbackServlet servlet = new AdminFeedbackServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void invalidAction_redirectsError() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("update");

        AdminFeedbackServlet servlet = new AdminFeedbackServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void invalidId_redirectsError() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("delete");
        when(ctx.req.getParameter("id")).thenReturn("bad");

        AdminFeedbackServlet servlet = new AdminFeedbackServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
