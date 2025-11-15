package com.JAVA.Filters;

import java.io.IOException;

import com.JAVA.Beans.UserBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/driver/*")
public class DriverFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            UserBean user = (UserBean) session.getAttribute("user");
            if (user != null && "DRIVER".equals(user.getRole())) {
                chain.doFilter(request, response);
                return;
            }
        }

        String contextPath = httpRequest.getContextPath();
        httpResponse.sendRedirect(contextPath + "/login?error=driver_access_required");
    }
}
