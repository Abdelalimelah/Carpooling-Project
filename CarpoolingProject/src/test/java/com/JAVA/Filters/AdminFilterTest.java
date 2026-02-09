package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AdminFilterTest {
    @Test
    void testDoFilter_AdminUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("admin");
        
        // AJOUT : Simuler une URL valide pour passer les checks du filtre
        when(request.getRequestURI()).thenReturn("/admin/dashboard.jsp");
        when(request.getContextPath()).thenReturn("");

        AdminFilter filter = new AdminFilter();
        filter.doFilter(request, response, chain);
        
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_NotAdminUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("user"); // Pas admin
        
        // AJOUT
        when(request.getRequestURI()).thenReturn("/admin/dashboard.jsp");
        when(request.getContextPath()).thenReturn("");

        AdminFilter filter = new AdminFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}