package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CreateTripServletIT extends BaseServletTest {

    @Test
    void doGet_withCars_forwards() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        CreateTripServlet servlet = new CreateTripServlet();
        servlet.doGet(ctx.req, ctx.resp);
        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void doPost_valid_redirects() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("departureCity")).thenReturn("Paris");
        when(ctx.req.getParameter("arrivalCity")).thenReturn("Lyon");
        when(ctx.req.getParameter("dateTime")).thenReturn("2030-01-01T10:00");
        when(ctx.req.getParameter("price")).thenReturn("20.0");
        when(ctx.req.getParameter("seatsAvailable")).thenReturn("2");
        when(ctx.req.getParameter("carId")).thenReturn(String.valueOf(TestDb.CAR_ID));

        CreateTripServlet servlet = new CreateTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect("/app/driver/dashboard?tripCreated=1");
    }

    @Test
    void doPost_invalidDate_forwardsWithCars() throws Exception {
        var ctx = ctx(user(TestDb.DRIVER_USER_ID, "DRIVER"));
        when(ctx.req.getParameter("departureCity")).thenReturn("Paris");
        when(ctx.req.getParameter("arrivalCity")).thenReturn("Lyon");
        when(ctx.req.getParameter("dateTime")).thenReturn("bad-date");
        when(ctx.req.getParameter("price")).thenReturn("20.0");
        when(ctx.req.getParameter("seatsAvailable")).thenReturn("2");
        when(ctx.req.getParameter("carId")).thenReturn(String.valueOf(TestDb.CAR_ID));

        CreateTripServlet servlet = new CreateTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }
}
