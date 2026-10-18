<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/tripDetails.css">
</head>
<body>
    <div class="trip-details-wrapper">
        <!-- Header -->
        <header class="details-header">
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
                    <a href="${pageContext.request.contextPath}/passenger/dashboard" class="btn btn-outline">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 12H5M12 19l-7-7 7-7"/>
                        </svg>
                        Back to Dashboard
                    </a>
                </nav>
            </div>
        </header>

        <!-- Main Content -->
        <main class="details-main">
            <div class="container">
                <!-- Page Header -->
                <div class="page-header">
                    <h1>Trip Details</h1>
                    <p>Complete information about your selected trip</p>
                </div>

                <!-- Trip Overview -->
                <div class="trip-overview-section">
                    <div class="section-header">
                        <h2>${trip.departureCity} → ${trip.arrivalCity}</h2>
                        <div class="seats-indicator ${seatsLeft > 0 ? 'available' : 'unavailable'}">
                            <c:choose>
                                <c:when test="${seatsLeft > 0}">
                                    ${seatsLeft} seat(s) available
                                </c:when>
                                <c:otherwise>
                                    No seats available
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="trip-overview-grid">
                        <div class="overview-card">
                            <div class="overview-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                    <line x1="8" y1="2" x2="8" y2="6"/>
                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                </svg>
                            </div>
                            <div class="overview-content">
                                <span class="overview-label">Date & Time</span>
                                <span class="overview-value">
                                    <fmt:parseDate value="${trip.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" />
                                    <fmt:formatDate value="${parsedDateTime}" pattern="EEEE, MMMM dd, yyyy 'at' HH:mm" />
                                </span>
                            </div>
                        </div>

                        <div class="overview-card">
                            <div class="overview-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <line x1="12" y1="1" x2="12" y2="23"/>
                                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                                </svg>
                            </div>
                            <div class="overview-content">
                                <span class="overview-label">Price</span>
                                <span class="overview-value">${trip.price} MAD</span>
                            </div>
                        </div>

                        <div class="overview-card">
                            <div class="overview-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z"/>
                                    <circle cx="12" cy="9" r="2.5"/>
                                </svg>
                            </div>
                            <div class="overview-content">
                                <span class="overview-label">Departure</span>
                                <span class="overview-value">${trip.departureCity}</span>
                            </div>
                        </div>

                        <div class="overview-card">
                            <div class="overview-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
                                    <circle cx="12" cy="10" r="3"/>
                                </svg>
                            </div>
                            <div class="overview-content">
                                <span class="overview-label">Destination</span>
                                <span class="overview-value">${trip.arrivalCity}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Driver Information -->
                <div class="info-section">
                    <div class="section-header">
                        <h2>Driver Information</h2>
                    </div>
                    
                    <c:choose>
                        <c:when test="${driverUser != null}">
                            <div class="info-card">
                                <div class="info-header">
                                    <div class="user-avatar large">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                            <circle cx="12" cy="7" r="4"/>
                                        </svg>
                                    </div>
                                    <div class="user-info">
                                        <h3>${driverUser.firstName} ${driverUser.lastName}</h3>
                                        <c:if test="${driverUser.phoneNumber != null}">
                                            <p class="user-contact">${driverUser.phoneNumber}</p>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="info-actions">
                                    <c:if test="${driverUser.phoneNumber != null}">
                                        <a href="tel:${driverUser.phoneNumber}" class="btn btn-outline">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                                            </svg>
                                            Call Driver
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state small">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                                <h3>Driver Information Unavailable</h3>
                                <p>Driver details are not available at the moment.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Car Information -->
                <div class="info-section">
                    <div class="section-header">
                        <h2>Car Information</h2>
                    </div>
                    
                    <c:choose>
                        <c:when test="${car != null}">
                            <div class="info-card">
                                <div class="info-header">
                                    <div class="car-icon large">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                            <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                        </svg>
                                    </div>
                                    <div class="car-info">
                                        <h3>${car.brand} ${car.model}</h3>
                                        <p class="car-details">License Plate: ${car.plateNumber}</p>
                                    </div>
                                </div>
                                <div class="car-features">
                                    <div class="feature-item">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <rect x="2" y="5" width="20" height="14" rx="2"/>
                                            <line x1="2" y1="10" x2="22" y2="10"/>
                                        </svg>
                                        <span>Verified Vehicle</span>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state small">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                    <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                </svg>
                                <h3>Car Information Unavailable</h3>
                                <p>Vehicle details are not available at the moment.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>

        </main>
    </div>
</body>
</html>