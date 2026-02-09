package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class DriverFilterTest {
    @Test
    void testDoFilter_DriverUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("driver");
        
        // AJOUT : Simuler une URL de conducteur
        when(request.getRequestURI()).thenReturn("/driver/schedule.jsp");
        when(request.getContextPath()).thenReturn("");

        DriverFilter filter = new DriverFilter();
        filter.doFilter(request, response, chain);
        
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_NotDriverUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("user");
        
        // AJOUT
        when(request.getRequestURI()).thenReturn("/driver/schedule.jsp");
        when(request.getContextPath()).thenReturn("");

        DriverFilter filter = new DriverFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}