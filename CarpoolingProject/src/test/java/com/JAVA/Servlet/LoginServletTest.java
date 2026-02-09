package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginServletTest extends BaseServletTest {

    @Test
    void missingEmailOrPassword_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("email")).thenReturn("");
        when(ctx.req.getParameter("password")).thenReturn("");

        LoginServlet servlet = new LoginServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
