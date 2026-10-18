package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.*;
import static org.mockito.Mockito.*;

class LoginServletIT {
    @Test
    void testLoginSuccess() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("pass1");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");
        LoginServlet servlet = new LoginServlet();
        servlet.service(request, response);
        verify(session, atLeastOnce()).setAttribute(eq("user"), any());
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
