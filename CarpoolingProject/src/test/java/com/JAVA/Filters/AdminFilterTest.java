package com.JAVA.Filters;

import com.JAVA.Beans.UserBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdminFilterTest {

    @Test
    void allowsAdmin() throws Exception {
        AdminFilter filter = new AdminFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);

        UserBean user = new UserBean();
        user.setRole("ADMIN");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        filter.doFilter(req, resp, chain);

        verify(chain).doFilter(req, resp);
        verify(resp, never()).sendRedirect(anyString());
    }

    @Test
    void redirectsNonAdmin() throws Exception {
        AdminFilter filter = new AdminFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getSession(false)).thenReturn(session);
        when(req.getContextPath()).thenReturn("/app");
        when(session.getAttribute("user")).thenReturn(null);

        filter.doFilter(req, resp, chain);

        verify(resp).sendRedirect("/app/login?error=admin_access_required");
        verify(chain, never()).doFilter(req, resp);
    }
}
