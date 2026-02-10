<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - RideShare Carpooling</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
    <div class="login-wrapper">
        <!-- Left Side - Form -->
        <div class="form-side">
            <div class="form-container">
                <!-- Logo & Header -->
                <div class="logo-container">
                    <div class="logo-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                            <circle cx="7" cy="17" r="2"/>
                            <path d="M9 17h6"/>
                            <circle cx="17" cy="17" r="2"/>
                        </svg>
                    </div>
                    <h1>Welcome Back</h1>
                    <p>Sign in to continue your journey</p>
                </div>

                <%-- Success message after registration --%>
                <c:if test="${not empty param.registered}">
                    <div class="message success">Registration successful! Please log in.</div>
                </c:if>
                
                <%-- Success message after logout --%>
                <c:if test="${not empty param.logout}">
                    <div class="message success">You have been logged out.</div>
                </c:if>
                
                <%-- Error message --%>
                <c:if test="${not empty errorMessage}">
                    <div class="message error"><c:out value="${errorMessage}" /></div>
                </c:if>

                <!-- Login Form -->
                <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect width="20" height="16" x="2" y="4" rx="2"/>
                                <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                            </svg>
                            <input type="email" id="email" name="email" placeholder="you@example.com" required autofocus>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect width="18" height="11" x="3" y="11" rx="2" ry="2"/>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                            </svg>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
                            <polyline points="10 17 15 12 10 7"/>
                            <line x1="15" x2="3" y1="12" y2="12"/>
                        </svg>
                        Sign In
                    </button>

                    <div class="signup-link">
                        Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up now</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Right Side - Hero Image -->
        <div class="hero-side">
            <img src="https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?auto=format&fit=crop&q=80&w=2070" alt="Carpooling journey" class="hero-image">
            <div class="hero-overlay"></div>
            
            <div class="hero-content">
                <h2>Share the Ride,<br>Share the Journey</h2>
                <p>Join thousands of commuters who save money and reduce their carbon footprint by carpooling together.</p>
                
                <div class="hero-stats">
                    <div class="stat">
                        <span class="stat-value">50K+</span>
                        <span class="stat-label">Active Riders</span>
                    </div>
                    <div class="stat">
                        <span class="stat-value">1M+</span>
                        <span class="stat-label">Trips Completed</span>
                    </div>
                    <div class="stat">
                        <span class="stat-value">85%</span>
                        <span class="stat-label">Satisfaction</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>