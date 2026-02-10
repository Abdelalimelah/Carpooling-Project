<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RideShare - Modern Carpooling Platform</title>
    <meta name="description" content="Join RideShare to find carpooling partners, save money, and reduce your carbon footprint. Share rides with trusted commuters.">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
</head>
<body>
    <div class="hero-container">
        <div class="hero-content">
            <!-- Car Icon -->
            <div class="icon-container">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                    <circle cx="7" cy="17" r="2"/>
                    <path d="M9 17h6"/>
                    <circle cx="17" cy="17" r="2"/>
                </svg>
            </div>

            <!-- Main Heading -->
            <h1 class="hero-title">Welcome to RideShare</h1>

            <!-- Description -->
            <p class="hero-description">
                Your journey to smarter, greener commuting starts here
            </p>

            <!-- CTA Button -->
            <a href="${pageContext.request.contextPath}/views/auth/login.jsp" class="cta-button">
                Get Started
            </a>
        </div>
    </div>
</body>
</html>
