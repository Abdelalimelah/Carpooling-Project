package com.JAVA.Servlet;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminBookingServletIT extends BaseServletTest {

    @Test
    void confirmBooking_redirects() throws Exception {
        var ctx = ctx(user(TestDb.ADMIN_USER_ID, "ADMIN"));
        when(ctx.req.getParameter("action")).thenReturn("confirm");
        when(ctx.req.getParameter("id")).thenReturn(String.valueOf(TestDb.BOOKING_CONFIRMED_ID));

        AdminBookingServlet servlet = new AdminBookingServlet();
        servlet.doPost(ctx.req, ctx.resp);

        verify(ctx.resp).sendRedirect(anyString());
    }
}
