<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - RideShare Carpooling</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">
</head>
<body>
    <div class="register-wrapper">
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
                    <h1>Join RideShare</h1>
                    <p>Create your account to start sharing rides</p>
                </div>

                <%-- Error message --%>
                <c:if test="${not empty errorMessage}">
                    <div class="message error"><c:out value="${errorMessage}" /></div>
                </c:if>
                
                <%-- Success message --%>
                <c:if test="${not empty param.success}">
                    <div class="message success">Registration successful! You can now log in.</div>
                </c:if>

                <!-- Register Form -->
                <form class="register-form" action="${pageContext.request.contextPath}/register" method="post">
                    <!-- Role Selection -->
                    <div class="form-group">
                        <label>Register as:</label>
                        <div class="role-selection">
                            <div class="role-option">
                                <input type="radio" id="rolePassenger" name="role" value="PASSENGER" 
                                    <c:if test="${empty selectedRole or selectedRole == 'PASSENGER'}">checked</c:if>>
                                <label for="rolePassenger">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                        <circle cx="12" cy="7" r="4"/>
                                    </svg>
                                    Passenger
                                </label>
                            </div>
                            <div class="role-option">
                                <input type="radio" id="roleDriver" name="role" value="DRIVER" 
                                    <c:if test="${selectedRole == 'DRIVER'}">checked</c:if>>
                                <label for="roleDriver">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                        <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                    </svg>
                                    Driver
                                </label>
                            </div>
                        </div>
                    </div>

                    <!-- Personal Information -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                                <input type="text" id="firstName" name="firstName" 
                                    value="<c:out value='${firstName}'/>" placeholder="John" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                                <input type="text" id="lastName" name="lastName" 
                                    value="<c:out value='${lastName}'/>" placeholder="Doe" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect width="20" height="16" x="2" y="4" rx="2"/>
                                <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                            </svg>
                            <input type="email" id="email" name="email" 
                                value="<c:out value='${email}'/>" placeholder="you@example.com" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                            </svg>
                            <input type="tel" id="phoneNumber" name="phoneNumber" 
                                value="<c:out value='${phoneNumber}'/>" placeholder="+1 234 567 8900">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect width="18" height="11" x="3" y="11" rx="2" ry="2"/>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                            </svg>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required minlength="6">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <div class="input-wrapper">
                            <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect width="18" height="11" x="3" y="11" rx="2" ry="2"/>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                                <path d="m9 11 2 2 4-4"/>
                            </svg>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your password" required minlength="6">
                        </div>
                        <small class="input-help">Make sure both passwords match</small>
                    </div>

                    <!-- Driver-specific fields -->
                    <div id="driverFields" class="driver-fields" 
                        style="display: ${selectedRole == 'DRIVER' ? 'block' : 'none'};">
                        <h3>Driver Information</h3>
                        
                        <div class="form-group">
                            <label for="licenceNumber">Driver's License Number</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="2" y="5" width="20" height="14" rx="2"/>
                                    <line x1="2" y1="10" x2="22" y2="10"/>
                                </svg>
                                <input type="text" id="licenceNumber" name="licenceNumber" 
                                    value="<c:out value='${licenceNumber}'/>" placeholder="DL123456789">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="carBrand">Car Brand</label>
                                <div class="input-wrapper">
                                    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                        <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                    </svg>
                                    <input type="text" id="carBrand" name="carBrand" 
                                        value="<c:out value='${carBrand}'/>" placeholder="Toyota">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="carModel">Car Model</label>
                                <div class="input-wrapper">
                                    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                        <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                    </svg>
                                    <input type="text" id="carModel" name="carModel" 
                                        value="<c:out value='${carModel}'/>" placeholder="Camry">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="plateNumber">License Plate Number</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="3" y="8" width="18" height="8" rx="2"/>
                                    <path d="M7 11h2m3 0h2m3 0h2"/>
                                </svg>
                                <input type="text" id="plateNumber" name="plateNumber" 
                                    value="<c:out value='${plateNumber}'/>" placeholder="ABC-1234">
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
                            <polyline points="17 21 17 13 7 13 7 21"/>
                            <polyline points="7 3 7 8 15 8"/>
                        </svg>
                        Create Account
                    </button>

                    <div class="login-link">
                        Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>
                    </div>

                    <div class="form-footer">
                        By registering, you agree to our 
                        <a href="#">Terms of Service</a> and 
                        <a href="#">Privacy Policy</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Right Side - Hero Image -->
        <div class="hero-side">
            <img src="https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?auto=format&fit=crop&q=80&w=2070" alt="Carpooling community" class="hero-image">
            <div class="hero-overlay"></div>
            
            <div class="hero-content">
                <h2>Start Your Journey<br>With RideShare</h2>
                <p>Join thousands of commuters who save money and reduce their carbon footprint by carpooling together.</p>
                
                <div class="hero-stats">
                    <div class="stat">
                        <span class="stat-value">50K+</span>
                        <span class="stat-label">Active Members</span>
                    </div>
                    <div class="stat">
                        <span class="stat-value">1M+</span>
                        <span class="stat-label">Trips Completed</span>
                    </div>
                    <div class="stat">
                        <span class="stat-value">4.8★</span>
                        <span class="stat-label">Average Rating</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/assets/js/register.js"></script>
</body>
</html>