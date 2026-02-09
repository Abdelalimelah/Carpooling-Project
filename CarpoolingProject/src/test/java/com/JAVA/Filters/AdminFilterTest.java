package com.JAVA.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import com.JAVA.Beans.User; // IMPORT DU BEAN USER

import static org.mockito.Mockito.*;

class AdminFilterTest {
    @Test
    void testDoFilter_AdminUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        FilterChain chain = mock(FilterChain.class);
        
        // 1. Créer un Mock de l'objet User
        User adminUser = mock(User.class);
        when(adminUser.getRole()).thenReturn("admin"); // On définit son rôle

        when(request.getSession(false)).thenReturn(session);
        // 2. CORRECTION : On met l'objet User dans la session (pas juste le string role)
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
        
        // 1. Créer un User "lambda"
        User simpleUser = mock(User.class);
        when(simpleUser.getRole()).thenReturn("user"); // Rôle simple

        when(request.getSession(false)).thenReturn(session);
        // 2. CORRECTION
        when(session.getAttribute("user")).thenReturn(simpleUser); 
        
        when(request.getRequestURI()).thenReturn("/admin/dashboard.jsp");
        when(request.getContextPath()).thenReturn("");

        AdminFilter filter = new AdminFilter();
        filter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}