<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User - RideShare Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/editUser.css">
</head>
<body>
    <div class="dashboard-wrapper">
        <!-- Header -->
        <header class="dashboard-header">
            <div class="header-content">
                <!-- Logo -->
                <div class="logo">
                    <div class="logo-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                            <circle cx="7" cy="17" r="2"/>
                            <path d="M9 17h6"/>
                            <circle cx="17" cy="17" r="2"/>
                        </svg>
                    </div>
                    <span class="logo-text">RideShare • Admin</span>
                </div>

                <!-- User Menu -->
                <div class="user-menu">
                    <!-- Profile -->
                    <div class="profile-container">
                        <button id="profileButton" class="profile-btn">
                            <div class="user-avatar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                            </div>
                            <span class="user-name">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
                            <svg class="dropdown-arrow" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <polyline points="6 9 12 15 18 9"/>
                            </svg>
                        </button>
                        
                        <div id="profileDropdown" class="profile-dropdown">
                            <a href="${pageContext.request.contextPath}/profile" class="dropdown-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                                Profile
                            </a>
                            <a href="${pageContext.request.contextPath}/logout" class="dropdown-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                                    <polyline points="16 17 21 12 16 7"/>
                                    <line x1="21" y1="12" x2="9" y2="12"/>
                                </svg>
                                Logout
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="dashboard-main">
            <div class="container">
                <div class="form-card">
                    <div class="form-header">
                        <h1>Edit User #${user.id}</h1>
                        <p>Update user information and manage their role</p>
                    </div>

                    <!-- Flash Messages -->
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-error">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="12" r="10"/>
                                <line x1="12" y1="8" x2="12" y2="12"/>
                                <line x1="12" y1="16" x2="12.01" y2="16"/>
                            </svg>
                            <span>${param.error}</span>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                                <polyline points="22 4 12 14.01 9 11.01"/>
                            </svg>
                            <span>${param.success}</span>
                        </div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/admin/users" class="user-form">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="id" value="${user.id}"/>

                        <div class="form-grid">
                            <!-- Email -->
                            <div class="form-group">
                                <label for="email" class="form-label">Email Address</label>
                                <input type="email" id="email" name="email" value="${user.email}" 
                                       class="form-input" required placeholder="user@example.com"/>
                                <div class="form-hint">User's primary email address for login</div>
                            </div>

                            <!-- First Name -->
                            <div class="form-group">
                                <label for="firstName" class="form-label">First Name</label>
                                <input type="text" id="firstName" name="firstName" value="${user.firstName}" 
                                       class="form-input" placeholder="John"/>
                            </div>

                            <!-- Last Name -->
                            <div class="form-group">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" id="lastName" name="lastName" value="${user.lastName}" 
                                       class="form-input" placeholder="Doe"/>
                            </div>

                            <!-- Phone Number -->
                            <div class="form-group">
                                <label for="phoneNumber" class="form-label">Phone Number</label>
                                <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" 
                                       class="form-input" placeholder="+212 6 00 00 00 00"/>
                            </div>

                            <!-- Role -->
                            <div class="form-group">
                                <label for="role" class="form-label">User Role</label>
                                <select id="role" name="role" class="form-select" required>
                                    <option value="PASSENGER" <c:if test="${user.role=='PASSENGER'}">selected</c:if>>PASSENGER</option>
                                    <option value="DRIVER" <c:if test="${user.role=='DRIVER'}">selected</c:if>>DRIVER</option>
                                    <option value="ADMIN" <c:if test="${user.role=='ADMIN'}">selected</c:if>>ADMIN</option>
                                </select>
                                <div class="form-hint">
                                    Changing role will automatically update driver/passenger records to maintain consistency.
                                </div>
                            </div>

                            <!-- Driver License (Conditional) -->
                            <div id="licenceRow" class="form-group" style="display: none;">
                                <label for="licenceNumber" class="form-label">Driver License Number</label>
                                <input type="text" id="licenceNumber" name="licenceNumber" value="${licenceNumber}" 
                                       class="form-input" placeholder="License number"/>
                                <div class="form-hint">Required when role is set to DRIVER</div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="form-actions">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 12H5M12 19l-7-7 7-7"/>
                                </svg>
                                Back to Dashboard
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
                                    <polyline points="17 21 17 13 7 13 7 21"/>
                                    <polyline points="7 3 7 8 15 8"/>
                                </svg>
                                Update User
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/editUser.js"></script>
</body>
</html>