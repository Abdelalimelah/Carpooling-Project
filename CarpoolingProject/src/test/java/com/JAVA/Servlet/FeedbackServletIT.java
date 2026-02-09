package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class FeedbackServletIT extends BaseServletTest {

    @Test
    void doGet_forwards() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("tripId")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));
        when(ctx.req.getParameter("targetId")).thenReturn(String.valueOf(TestDb.DRIVER_USER_ID));
        when(ctx.req.getParameter("type")).thenReturn("DRIVER");

        FeedbackServlet servlet = new FeedbackServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_valid_redirects() throws Exception {
        var ctx = ctx(user(TestDb.PASSENGER_USER_ID, "PASSENGER"));
        when(ctx.req.getParameter("tripId")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));
        when(ctx.req.getParameter("targetId")).thenReturn(String.valueOf(TestDb.DRIVER_USER_ID));
        when(ctx.req.getParameter("feedbackType")).thenReturn("DRIVER");
        when(ctx.req.getParameter("rating")).thenReturn("5");
        when(ctx.req.getParameter("comment")).thenReturn("Great");

        FeedbackServlet servlet = new FeedbackServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
