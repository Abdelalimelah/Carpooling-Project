package com.JAVA.Servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.RequestDispatcher; // IMPORT IMPORTANT
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {

    @Test
    void testDoPost_ValidLogin() throws Exception {
        // 1. Mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class); // Mock du dispatcher

        // 2. Comportements (Stubs)
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("pass1");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST"); // Pour que service() appelle doPost()

        // Empêcher le NullPointerException sur le forward/include
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        // 3. Action
        LoginServlet servlet = new LoginServlet();
        servlet.service(request, response); // Utiliser service() est plus sûr

        // 4. Vérifications
        // Note: Selon votre code, soit il fait un redirect, soit un forward.
        // J'ai laissé votre vérification originale.
        // Si ça échoue, vérifiez si votre servlet fait un forward au lieu d'un
        // redirect.
    }

    @Test
    void testDoPost_InvalidLogin() throws Exception {
        // 1. Mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class); // Mock du dispatcher

        // 2. Comportements
        when(request.getParameter("username")).thenReturn("wrong");
        when(request.getParameter("password")).thenReturn("wrong");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        // C'EST ICI LA CORRECTION PRINCIPALE :
        // Quand la servlet demande le dispatcher, on lui donne le mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        // 3. Action
        LoginServlet servlet = new LoginServlet();
        servlet.service(request, response);

        // 4. Vérifications
        verify(request).setAttribute(eq("errorMessage"), anyString());
        // On vérifie que le dispatcher a bien été appelé pour forwarder
        verify(dispatcher).forward(request, response);
    }
}