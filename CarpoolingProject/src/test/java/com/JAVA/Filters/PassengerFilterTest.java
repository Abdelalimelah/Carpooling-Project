package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import com.JAVA.Beans.UserBean; // IMPORT

import static org.mockito.Mockito.*;

class PassengerFilterTest {
    @Test
    void testDoFilter_PassengerUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        // 1. Mock User Passager
        User passengerUser = mock(User.class);
        when(passengerUser.getRole()).thenReturn("passenger");

        when(request.getSession(false)).thenReturn(session);
        // 2. Correction Session
        when(session.getAttribute("user")).thenReturn(passengerUser);
        
        when(request.getRequestURI()).thenReturn("/passenger/book.jsp");
        when(request.getContextPath()).thenReturn("");

        PassengerFilter filter = new PassengerFilter();
        filter.doFilter(request, response, chain);
        
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_NotPassengerUser() throws Exception {
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
        
        when(request.getRequestURI()).thenReturn("/passenger/book.jsp");
        when(request.getContextPath()).thenReturn("");

        PassengerFilter filter = new PassengerFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}