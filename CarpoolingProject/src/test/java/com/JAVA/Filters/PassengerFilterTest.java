package com.JAVA.Filters;

import com.JAVA.Beans.UserBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PassengerFilterTest {

    @Test
    void allowsPassenger() throws Exception {
        PassengerFilter filter = new PassengerFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);

        UserBean user = new UserBean();
        user.setRole("PASSENGER");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        filter.doFilter(req, resp, chain);
        verify(chain).doFilter(req, resp);
        verify(resp, never()).sendRedirect(anyString());
    }

    @Test
    void redirectsNonPassenger() throws Exception {
        PassengerFilter filter = new PassengerFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getSession(false)).thenReturn(null);
        when(req.getContextPath()).thenReturn("/app");

        filter.doFilter(req, resp, chain);

        verify(resp).sendRedirect("/app/login?error=passenger_access_required");
        verify(chain, never()).doFilter(req, resp);
    }
}
