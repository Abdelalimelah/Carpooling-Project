package com.JAVA.Filters;

import java.io.IOException;

import com.JAVA.Beans.UserBean;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/passenger/*")
public class PassengerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            UserBean user = (UserBean) session.getAttribute("user");
            if (user != null && "PASSENGER".equals(user.getRole())) {
                chain.doFilter(request, response);
                return;
            }
        }

        String contextPath = httpRequest.getContextPath();
        httpResponse.sendRedirect(contextPath + "/login?error=passenger_access_required");
    }
}
