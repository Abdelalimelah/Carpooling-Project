package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {
    @Test
    void testDoPost_ValidLogin() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("pass1");
        when(request.getSession()).thenReturn(session);

        LoginServlet servlet = new LoginServlet();
        servlet.doPost(request, response);

        verify(session, atLeastOnce()).setAttribute(eq("user"), any());
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPost_InvalidLogin() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("username")).thenReturn("wrong");
        when(request.getParameter("password")).thenReturn("wrong");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        LoginServlet servlet = new LoginServlet();
        servlet.service(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(request).getRequestDispatcher(anyString());
    }
}
