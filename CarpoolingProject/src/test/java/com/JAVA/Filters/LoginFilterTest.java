package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class LoginFilterTest {

    @Test
    void testDoFilter_LoggedIn() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new Object());
        
        // CORRECTION : Simuler une URL valide pour éviter le NullPointerException sur endsWith
        when(request.getRequestURI()).thenReturn("/secure/dashboard.jsp");
        when(request.getContextPath()).thenReturn(""); // Par sécurité

        LoginFilter filter = new LoginFilter();
        filter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_NotLoggedIn() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        
        // CORRECTION : Simuler une URL protégée qui nécessite une connexion
        when(request.getRequestURI()).thenReturn("/secure/profile.jsp");
        when(request.getContextPath()).thenReturn("");

        LoginFilter filter = new LoginFilter();
        filter.doFilter(request, response, chain);

        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}