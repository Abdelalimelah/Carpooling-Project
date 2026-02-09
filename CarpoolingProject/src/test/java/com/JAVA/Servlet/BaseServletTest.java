package com.JAVA.Servlet;

import com.JAVA.Beans.UserBean;
import com.JAVA.TestUtils.TestDb;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public abstract class BaseServletTest {

    @BeforeEach
    void resetDb() {
        TestDb.reset();
    }

    protected UserBean user(int id, String role) {
        UserBean u = new UserBean();
        u.setId(id);
        u.setRole(role);
        u.setFirstName("Test");
        u.setLastName("User");
        u.setEmail(role.toLowerCase() + "@test.local");
        return u;
    }

    protected Ctx ctx(UserBean user) {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession(false)).thenReturn(session);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getContextPath()).thenReturn("/app");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        return new Ctx(req, resp, session, dispatcher);
    }

    protected Ctx ctxNoSession() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession(false)).thenReturn(null);
        when(req.getContextPath()).thenReturn("/app");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        return new Ctx(req, resp, null, dispatcher);
    }

    protected StringWriter attachWriter(HttpServletResponse resp) throws IOException {
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));
        return sw;
    }

    protected static class Ctx {
        public final HttpServletRequest req;
        public final HttpServletResponse resp;
        public final HttpSession session;
        public final RequestDispatcher dispatcher;

        public Ctx(HttpServletRequest req, HttpServletResponse resp,
                   HttpSession session, RequestDispatcher dispatcher) {
            this.req = req;
            this.resp = resp;
            this.session = session;
            this.dispatcher = dispatcher;
        }
    }
}
