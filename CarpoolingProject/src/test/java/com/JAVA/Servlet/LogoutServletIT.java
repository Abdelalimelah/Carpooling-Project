package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.*;
import static org.mockito.Mockito.*;

class LogoutServletIT {
    @Test
    void testLogout() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        LogoutServlet servlet = new LogoutServlet();
        servlet.doGet(request, response);
        verify(session, atLeastOnce()).invalidate();
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
