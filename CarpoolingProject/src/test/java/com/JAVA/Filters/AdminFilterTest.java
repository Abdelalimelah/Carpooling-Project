package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import com.JAVA.Beans.UserBean; // Import correct

import static org.mockito.Mockito.*;

class AdminFilterTest {
    @Test
    void testDoFilter_AdminUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        // CORRECTION ICI : UserBean partout !
        UserBean adminUser = mock(UserBean.class);
        when(adminUser.getRole()).thenReturn("admin"); 

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser); 
        
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
        
        // CORRECTION ICI : UserBean partout !
        UserBean simpleUser = mock(UserBean.class);
        when(simpleUser.getRole()).thenReturn("user"); 

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(simpleUser); 
        
        when(request.getRequestURI()).thenReturn("/admin/dashboard.jsp");
        when(request.getContextPath()).thenReturn("");

        AdminFilter filter = new AdminFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}