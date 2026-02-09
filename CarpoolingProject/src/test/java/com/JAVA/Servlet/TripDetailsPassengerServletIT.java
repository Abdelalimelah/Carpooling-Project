package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.*;
import static org.mockito.Mockito.*;

class TripDetailsPassengerServletIT {
    @Test
    void testTripDetailsPassenger() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(request.getMethod()).thenReturn("GET");
        TripDetailsPassengerServlet servlet = new TripDetailsPassengerServlet();
        servlet.service(request, response);
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
    }
}
