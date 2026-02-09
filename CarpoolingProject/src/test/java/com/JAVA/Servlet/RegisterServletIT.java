package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.*;
import static org.mockito.Mockito.*;

class RegisterServletIT {
    @Test
    void testRegister() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("email")).thenReturn("test@mail.com");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getSession()).thenReturn(session);
        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
