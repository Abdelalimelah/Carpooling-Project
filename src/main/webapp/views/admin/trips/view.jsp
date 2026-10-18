<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip #${trip.id} - RideShare Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/tripView.css">
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
                <!-- Back Button -->
                <div class="page-header">
                    <a href="${ret}" class="btn btn-outline btn-back">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 12H5M12 19l-7-7 7-7"/>
                        </svg>
                        Back to Dashboard
                    </a>
                </div>

                <!-- Trip Details Card -->
                <div class="details-card">
                    <div class="details-header">
                        <h1>Trip #${trip.id}</h1>
                    </div>

                    <!-- Trip Information -->
                    <div class="details-grid">
                        <!-- Route Information -->
                        <div class="info-section">
                            <h2 class="section-title">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                                    <circle cx="7" cy="17" r="2"/>
                                    <path d="M9 17h6"/>
                                    <circle cx="17" cy="17" r="2"/>
                                </svg>
                                Route Information
                            </h2>
                            <div class="info-grid">
                                <div class="info-item">
                                    <span class="info-label">Departure City</span>
                                    <span class="info-value">${trip.departureCity}</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">Arrival City</span>
                                    <span class="info-value">${trip.arrivalCity}</span>
                                </div>
                                <div class="info-item full-width">
                                    <div class="route-visual">
                                        <span class="departure">${trip.departureCity}</span>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <line x1="5" y1="12" x2="19" y2="12"/>
                                            <polyline points="12 5 19 12 12 19"/>
                                        </svg>
                                        <span class="arrival">${trip.arrivalCity}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Trip Details -->
                        <div class="info-section">
                            <h2 class="section-title">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                    <line x1="8" y1="2" x2="8" y2="6"/>
                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                </svg>
                                Trip Details
                            </h2>
                            <div class="info-grid">
                                <div class="info-item">
                                    <span class="info-label">Date & Time</span>
                                    <span class="info-value">${fn:replace(trip.dateTime, 'T', ' ')}</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">Price</span>
                                    <span class="info-value price">${trip.price} MAD</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">Available Seats</span>
                                    <span class="info-value seats">${trip.seatsAvailable}</span>
                                </div>
                            </div>
                        </div>

                        <!-- Driver Information -->
                        <c:if test="${driverUser != null}">
                            <div class="info-section">
                                <h2 class="section-title">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                        <circle cx="12" cy="7" r="4"/>
                                    </svg>
                                    Driver Information
                                </h2>
                                <div class="info-grid">
                                    <div class="info-item">
                                        <span class="info-label">Driver Name</span>
                                        <span class="info-value">${driverUser.firstName} ${driverUser.lastName}</span>
                                    </div>
                                    <div class="info-item">
                                        <span class="info-label">Email</span>
                                        <span class="info-value">${driverUser.email}</span>
                                    </div>
                                    <div class="info-item">
                                        <span class="info-label">Phone</span>
                                        <span class="info-value">${driverUser.phoneNumber}</span>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <!-- Car Information -->
                        <c:if test="${car != null}">
                            <div class="info-section">
                                <h2 class="section-title">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                        <path d="M6 9h4.5"/>
                                        <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                        <path d="M6 12h2"/>
                                        <circle cx="16" cy="16" r="1.5"/>
                                        <circle cx="8.5" cy="16" r="1.5"/>
                                    </svg>
                                    Car Information
                                </h2>
                                <div class="info-grid">
                                    <div class="info-item">
                                        <span class="info-label">Brand & Model</span>
                                        <span class="info-value">${car.brand} ${car.model}</span>
                                    </div>
                                    <div class="info-item">
                                        <span class="info-label">License Plate</span>
                                        <span class="info-value plate">${car.plateNumber}</span>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- Bookings Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                <circle cx="9" cy="7" r="4"/>
                                <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                                <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                            </svg>
                            Bookings (${not empty bookings ? bookings.size() : 0})
                        </h2>
                    </div>

                    <c:choose>
                        <c:when test="${not empty bookings}">
                            <div class="table-container">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>Booking ID</th>
                                            <th>Passenger ID</th>
                                            <th>Status</th>
                                            <th>Booked At</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${bookings}" var="b">
                                            <tr>
                                                <td>${b.id}</td>
                                                <td>
                                                    <div class="passenger-info">
                                                        <span class="passenger-id">${b.passengerId}</span>
                                                        <c:if test="${bookingPassengerNames != null && bookingPassengerNames[b.passengerId] != null}">
                                                            <span class="passenger-name">${bookingPassengerNames[b.passengerId]}</span>
                                                        </c:if>
                                                    </div>
                                                </td>
                                                <td>
                                                    <span class="status-badge status-${b.status.toLowerCase()}">
                                                        ${b.status}
                                                    </span>
                                                </td>
                                                <td>${fn:replace(b.bookingDate, 'T', ' ')}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                    <circle cx="9" cy="7" r="4"/>
                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                                    <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                                </svg>
                                <h3>No Bookings</h3>
                                <p>No passengers have booked this trip yet.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Action Buttons -->
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/admin/trips?action=edit&id=${trip.id}" 
                       class="btn btn-outline">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                        </svg>
                        Edit Trip
                    </a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/trips" 
                          class="inline-form"
                          onsubmit="return confirm('Are you sure you want to delete this trip? This action cannot be undone.');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${trip.id}"/>
                        <input type="hidden" name="ret" value="${ret}"/>
                        <button type="submit" class="btn btn-danger">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <polyline points="3 6 5 6 21 6"/>
                                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                                <line x1="10" y1="11" x2="10" y2="17"/>
                                <line x1="14" y1="11" x2="14" y2="17"/>
                            </svg>
                            Delete Trip
                        </button>
                    </form>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/tripView.js"></script>
</body>
</html>