package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminTripServletIT extends BaseServletTest {

    @Test
    void viewTrip_forwards() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("view");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void editTrip_forwards() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("edit");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.dispatcher).forward(ctx.req, ctx.resp);
    }

    @Test
    void invalidAction_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("foo");

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doGet(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void updateTrip_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("update");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));
        when(ctx.req.getParameter("departureCity")).thenReturn("Paris");
        when(ctx.req.getParameter("arrivalCity")).thenReturn("Lyon");
        when(ctx.req.getParameter("dateTime")).thenReturn("2030-01-01T10:00");
        when(ctx.req.getParameter("price")).thenReturn("12.50");
        when(ctx.req.getParameter("seatsAvailable")).thenReturn("2");
        when(ctx.req.getParameter("carId")).thenReturn(String.valueOf(TestDb.CAR_ID));

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void forceComplete_alreadyCompleted_redirectsInfo() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("forceComplete");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_PAST_ID));

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }

    @Test
    void deleteTrip_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("delete");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.TRIP_FUTURE_ID));

        AdminTripServlet servlet = new AdminTripServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
