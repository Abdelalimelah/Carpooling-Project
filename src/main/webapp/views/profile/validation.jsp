<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/validationProfile.css">
</head>
<body>
    <div class="profile-wrapper">
        <!-- Header -->
        <header class="profile-header">
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
                    <span class="logo-text">RideShare</span>
                </div>

                <nav class="header-nav">
                    <a href="${pageContext.request.contextPath}/${user.role.toLowerCase()}/dashboard" class="btn btn-outline">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 12H5M12 19l-7-7 7-7"/>
                        </svg>
                        Back to Dashboard
                    </a>
                </nav>
            </div>
        </header>

        <!-- Main Content -->
        <main class="profile-main">
            <div class="container">
                <!-- Page Header -->
                <div class="page-header">
                    <h1>My Profile</h1>
                    <p>Your personal information and account details</p>
                </div>

                <!-- Success Message -->
                <c:if test="${not empty param.updated}">
                    <div class="message success">
                        Profile updated successfully!
                    </div>
                </c:if>

                <!-- Profile Information -->
                <div class="profile-section">
                    <div class="profile-card">
                        <div class="profile-header">
                            <div class="user-avatar large">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                            </div>
                            <div class="user-info">
                                <h2>${user.firstName} ${user.lastName}</h2>
                                <p class="user-email">${user.email}</p>
                            </div>
                        </div>

                        <div class="profile-details">
                            <div class="detail-group">
                                <h3>Personal Information</h3>
                                <div class="detail-item">
                                    <span class="detail-label">Full Name</span>
                                    <span class="detail-value">${user.firstName} ${user.lastName}</span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Email Address</span>
                                    <span class="detail-value">${user.email}</span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Phone Number</span>
                                    <span class="detail-value">
                                        <c:choose>
                                            <c:when test="${not empty user.phoneNumber}">
                                                ${user.phoneNumber}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">Not provided</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>

                            <div class="detail-group">
                                <h3>Account Information</h3>
                                <div class="detail-item">
                                    <span class="detail-label">Account Role</span>
                                    <span class="detail-value">
                                        <span class="role-badge ${user.role.toLowerCase()}">${user.role}</span>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="profile-actions">
                            <a href="${pageContext.request.contextPath}/profile/edit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 20h9"/>
                                    <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/>
                                </svg>
                                Edit Profile
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>