package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.*;
import static org.mockito.Mockito.*;

class CreateBookingServletIT {
    @Test
    void testCreateBooking() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");
        CreateBookingServlet servlet = new CreateBookingServlet();
        servlet.service(request, response);
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
