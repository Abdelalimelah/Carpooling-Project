package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import com.JAVA.Beans.User; // IMPORT

import static org.mockito.Mockito.*;

class DriverFilterTest {
    @Test
    void testDoFilter_DriverUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        // 1. Mock User Conducteur
        User driverUser = mock(User.class);
        when(driverUser.getRole()).thenReturn("driver");

        when(request.getSession(false)).thenReturn(session);
        // 2. Correction Session
        when(session.getAttribute("user")).thenReturn(driverUser);
        
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
        
        // 1. Mock User Lambda
        User simpleUser = mock(User.class);
        when(simpleUser.getRole()).thenReturn("user");

        when(request.getSession(false)).thenReturn(session);
        // 2. Correction Session
        when(session.getAttribute("user")).thenReturn(simpleUser);
        
        when(request.getRequestURI()).thenReturn("/driver/schedule.jsp");
        when(request.getContextPath()).thenReturn("");

        DriverFilter filter = new DriverFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}