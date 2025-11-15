package com.JAVA.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {
    "/driver/*",
    "/passenger/*",
    "/admin/*",
    "/feedback",
    "/profile/*"
})
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();

        // Allow login, register, and logout always
        if (path.endsWith("/login") || path.endsWith("/logout") || path.endsWith("/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login?error=access_denied");
        } else {
            chain.doFilter(request, response);
        }
    }
}
