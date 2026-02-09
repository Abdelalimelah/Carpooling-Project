package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EditTripServletIT extends BaseServletTest {

    @Test
    void doGet_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        EditTripServlet servlet = new EditTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_valid_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));
        when(ctx.req.getParameter("departureCity")).thenReturn("Paris");
        when(ctx.req.getParameter("arrivalCity")).thenReturn("Nice");
        when(ctx.req.getParameter("dateTime")).thenReturn("2030-01-01T10:00");
        when(ctx.req.getParameter("price")).thenReturn("25.00");
        when(ctx.req.getParameter("seatsAvailable")).thenReturn("3");
        when(ctx.req.getParameter("carId")).thenReturn(String.valueOf(TestDb.CAR_ID));

        EditTripServlet servlet = new EditTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?tripUpdated=1");
    }

    @Test
    void doPost_invalidDate_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));
        when(ctx.req.getParameter("departureCity")).thenReturn("Paris");
        when(ctx.req.getParameter("arrivalCity")).thenReturn("Nice");
        when(ctx.req.getParameter("dateTime")).thenReturn("bad-date");
        when(ctx.req.getParameter("price")).thenReturn("25.00");
        when(ctx.req.getParameter("seatsAvailable")).thenReturn("3");
        when(ctx.req.getParameter("carId")).thenReturn(String.valueOf(TestDb.CAR_ID));

        EditTripServlet servlet = new EditTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
