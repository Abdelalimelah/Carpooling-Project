<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Dashboard - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/driverDashboard.css">
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
                    <span class="logo-text">RideShare</span>
                </div>

                <!-- User Menu -->
                <div class="user-menu">
                    <!-- Notifications -->
                    <div class="notification-container">
                        <button id="notificationBell" class="notification-btn" aria-haspopup="true" aria-expanded="false">
                            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
                                <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
                            </svg>
                            <c:if test="${unreadNotificationCount > 0}">
                                <span class="notification-badge">${unreadNotificationCount}</span>
                            </c:if>
                        </button>
                        
                        <div id="notificationDropdown" class="notification-dropdown">
                            <div class="notification-header">
                                <h3>Notifications</h3>
                            </div>
                            <div id="notificationDropdownList" class="notification-list">
                                <!-- Notifications will be loaded here -->
                            </div>
                        </div>
                    </div>

                    <!-- Profile -->
                    <div class="profile-container">
                        <button id="profileButton" class="profile-btn">
                            <div class="user-avatar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                    <circle cx="12" cy="7" r="4"/>
                                </svg>
                            </div>
                            <span class="user-name">${user.firstName} ${user.lastName}</span>
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
                <!-- Welcome Section -->
                <div class="welcome-section">
                    <h1>Welcome back, ${user.firstName}!</h1>
                    <p>Manage your cars, trips, and track your driver performance.</p>
                    <a href="${pageContext.request.contextPath}/driver/trips/create" class="btn btn-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"/>
                            <line x1="12" y1="8" x2="12" y2="16"/>
                            <line x1="8" y1="12" x2="16" y2="12"/>
                        </svg>
                        Publish New Trip
                    </a>
                </div>

                <!-- Driver Stats -->
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${totalTrips}</span>
                            <span class="stat-label">Total Trips</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <line x1="12" y1="1" x2="12" y2="23"/>
                                <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${totalEarnings}</span>
                            <span class="stat-label">Total Earnings (MAD)</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">
                                <c:choose>
                                    <c:when test="${averageRating > 0}">${averageRating}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </span>
                            <span class="stat-label">Average Rating</span>
                            <c:if test="${averageRating > 0}">
                                <div class="rating-stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= averageRating}">★</c:when>
                                            <c:otherwise>☆</c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!-- Cars Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>My Cars</h2>
                        <div class="section-actions">
                            <a href="${pageContext.request.contextPath}/driver/cars/add" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <circle cx="12" cy="12" r="10"/>
                                    <line x1="12" y1="8" x2="12" y2="16"/>
                                    <line x1="8" y1="12" x2="16" y2="12"/>
                                </svg>
                                Add Car
                            </a>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty cars}">
                            <div class="cars-grid">
                                <c:forEach items="${cars}" var="car">
                                    <div class="car-card">
                                        <div class="car-header">
                                            <h3>${car.brand} ${car.model}</h3>
                                            <span class="plate-number">${car.plateNumber}</span>
                                        </div>
                                        <div class="car-details">
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                                    <path d="M6 9h4.5"/>
                                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                                    <path d="M6 12h2"/>
                                                    <circle cx="16" cy="16" r="1.5"/>
                                                    <circle cx="8.5" cy="16" r="1.5"/>
                                                </svg>
                                                <span>${car.brand}</span>
                                            </div>
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                                    <path d="M6 9h4.5"/>
                                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                                    <path d="M6 12h2"/>
                                                    <circle cx="16" cy="16" r="1.5"/>
                                                    <circle cx="8.5" cy="16" r="1.5"/>
                                                </svg>
                                                <span>${car.model}</span>
                                            </div>
                                        </div>
                                        <div class="car-actions">
                                            <a href="${pageContext.request.contextPath}/driver/cars/edit?id=${car.id}" class="btn btn-sm btn-outline">Edit</a>
                                            <a href="${pageContext.request.contextPath}/driver/cars/delete?id=${car.id}" 
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Are you sure you want to delete this car?')">Delete</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                    <path d="M6 9h4.5"/>
                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                    <path d="M6 12h2"/>
                                    <circle cx="16" cy="16" r="1.5"/>
                                    <circle cx="8.5" cy="16" r="1.5"/>
                                </svg>
                                <h3>No Cars Registered</h3>
                                <p>Add your first car to start publishing trips.</p>
                                <a href="${pageContext.request.contextPath}/driver/cars/add" class="btn btn-primary">Add Your First Car</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Active Trips Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Active Trips</h2>
                        <div class="section-actions">
                            <a href="${pageContext.request.contextPath}/driver/trips/create" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <circle cx="12" cy="12" r="10"/>
                                    <line x1="12" y1="8" x2="12" y2="16"/>
                                    <line x1="8" y1="12" x2="16" y2="12"/>
                                </svg>
                                Publish Trip
                            </a>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty activeTrips}">
                            <div class="trips-grid">
                                <c:forEach items="${activeTrips}" var="trip">
                                    <div class="trip-card">
                                        <div class="trip-header">
                                            <div class="route">
                                                <span class="departure">${trip.departureCity}</span>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="5" y1="12" x2="19" y2="12"/>
                                                    <polyline points="12 5 19 12 12 19"/>
                                                </svg>
                                                <span class="arrival">${trip.arrivalCity}</span>
                                            </div>
                                            <div class="trip-price">${trip.price} MAD</div>
                                        </div>
                                        
                                        <div class="trip-details">
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                                    <line x1="8" y1="2" x2="8" y2="6"/>
                                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                                </svg>
                                                <span>
                                                    <fmt:parseDate value="${trip.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" />
                                                    <fmt:formatDate value="${parsedDateTime}" pattern="dd/MM/yyyy HH:mm" />
                                                </span>
                                            </div>
                                            
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                                    <circle cx="9" cy="7" r="4"/>
                                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                                                    <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                                                </svg>
                                                <span>${trip.seatsAvailable} seats available</span>
                                            </div>
                                        </div>
                                        
                                        <div class="trip-actions">
                                            <a href="${pageContext.request.contextPath}/driver/trips/details?id=${trip.id}" class="btn btn-sm btn-primary">Details</a>
                                            <a href="${pageContext.request.contextPath}/driver/trips/edit?id=${trip.id}" class="btn btn-sm btn-outline">Edit</a>
                                            <c:if test="${trip.dateTime.isBefore(now)}">
                                                <a href="${pageContext.request.contextPath}/driver/trips/complete?id=${trip.id}"
                                                   class="btn btn-sm btn-success"
                                                   onclick="return confirm('Mark this trip as completed?')">
                                                    Mark Completed
                                                </a>
                                            </c:if>
                                            <a href="${pageContext.request.contextPath}/driver/trips/delete?id=${trip.id}" 
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Are you sure you want to delete this trip?')">Delete</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                                    <circle cx="7" cy="17" r="2"/>
                                    <path d="M9 17h6"/>
                                    <circle cx="17" cy="17" r="2"/>
                                </svg>
                                <h3>No Active Trips</h3>
                                <p>You don't have any active trips at the moment.</p>
                                <a href="${pageContext.request.contextPath}/driver/trips/create" class="btn btn-primary">Publish Your First Trip</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Trip History Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Trip History</h2>
                    </div>

                    <c:choose>
                        <c:when test="${not empty completedTrips}">
                            <div class="trips-grid">
                                <c:forEach items="${completedTrips}" var="trip">
                                    <div class="trip-card completed">
                                        <div class="trip-header">
                                            <div class="route">
                                                <span class="departure">${trip.departureCity}</span>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="5" y1="12" x2="19" y2="12"/>
                                                    <polyline points="12 5 19 12 12 19"/>
                                                </svg>
                                                <span class="arrival">${trip.arrivalCity}</span>
                                            </div>
                                            <div class="trip-status completed">Completed</div>
                                        </div>
                                        
                                        <div class="trip-details">
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                                    <line x1="8" y1="2" x2="8" y2="6"/>
                                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                                </svg>
                                                <span>
                                                    <fmt:parseDate value="${trip.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" />
                                                    <fmt:formatDate value="${parsedDateTime}" pattern="dd/MM/yyyy HH:mm" />
                                                </span>
                                            </div>
                                            
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="12" y1="1" x2="12" y2="23"/>
                                                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                                                </svg>
                                                <span>${trip.price} MAD</span>
                                            </div>
                                        </div>
                                        
                                        <div class="trip-actions">
                                            <a href="${pageContext.request.contextPath}/driver/trips/details?id=${trip.id}" class="btn btn-sm btn-outline">View Details</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                                    <polyline points="14 2 14 8 20 8"/>
                                    <line x1="16" y1="13" x2="8" y2="13"/>
                                    <line x1="16" y1="17" x2="8" y2="17"/>
                                    <polyline points="10 9 9 9 8 9"/>
                                </svg>
                                <h3>No Trip History</h3>
                                <p>Your completed trips will appear here.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>

    <script>
        (function () {
            var appRoot = document.getElementById('appRoot');
            var ctx = (appRoot && appRoot.getAttribute('data-ctx')) ? appRoot.getAttribute('data-ctx') : '${pageContext.request.contextPath}';

            var notifBell = document.getElementById('notificationBell');
            var notifDropdown = document.getElementById('notificationDropdown');
            var notifList = document.getElementById('notificationDropdownList');
            var profileBtn = document.getElementById('profileButton');
            var profileDropdown = document.getElementById('profileDropdown');

            function hideAll() {
                if (notifDropdown) notifDropdown.style.display = 'none';
                if (profileDropdown) profileDropdown.style.display = 'none';
            }

            function parseMetadata(md) {
                if (!md) return {};
                if (typeof md === 'object') return md;
                try { return JSON.parse(md); } catch (e) { return {}; }
            }

            function absoluteUrl(url) {
                if (!url) return '#';
                if (url.indexOf('http://') === 0 || url.indexOf('https://') === 0) return url;
                if (ctx && url.indexOf(ctx) === 0) return url;
                return (url.charAt(0) === '/') ? (ctx + url) : (ctx + '/' + url);
            }

            function renderNotifications(items) {
                notifList.innerHTML = '';
                if (!items || !items.length) {
                    notifList.innerHTML = '<div class="notification-empty">No new notifications</div>';
                    return;
                }
                items.forEach(function (n) {
                    var meta = parseMetadata(n.metadata);
                    var row = document.createElement('div');
                    row.className = 'notification-item';

                    var msg = (n.message || '');
                    var created = (n.createdAt || '');
                    var body =
                        '<div class="notification-message">' + msg + '</div>' +
                        '<div class="notification-time">' + created + '</div>' +
                        (meta.cta ? '<div class="notification-cta">' + meta.cta + '</div>' : '');

                    if (meta.url) {
                        var a = document.createElement('a');
                        a.href = absoluteUrl(meta.url);
                        a.className = 'notification-link';
                        a.innerHTML = body;
                        row.appendChild(a);
                    } else {
                        row.innerHTML = body;
                    }

                    notifList.appendChild(row);
                });
            }

            function loadUnread() {
                return fetch(ctx + '/notifications/unread?limit=10', { credentials: 'same-origin' })
                    .then(function (r) {
                        if (!r.ok) throw new Error('Failed to load notifications');
                        return r.json();
                    })
                    .then(function (data) {
                        renderNotifications(data.items);
                    });
            }

            function markAllRead() {
                return fetch(ctx + '/notifications/mark-read', {
                    method: 'POST',
                    credentials: 'same-origin'
                }).then(function (r) {
                    if (!(r.ok || r.status === 204)) {
                        console.warn('Mark-read failed', r.status);
                    }
                    var badge = notifBell.querySelector('.notification-badge');
                    if (badge) badge.remove();
                });
            }

            if (notifBell) {
                notifBell.addEventListener('click', function (e) {
                    e.stopPropagation();
                    var open = (notifDropdown.style.display === 'block');
                    hideAll();
                    if (!open) {
                        notifDropdown.style.display = 'block';
                        notifBell.setAttribute('aria-expanded', 'true');
                        loadUnread().then(markAllRead).catch(function (err) { console.error(err); });
                    } else {
                        notifBell.setAttribute('aria-expanded', 'false');
                    }
                });
            }

            if (profileBtn) {
                profileBtn.addEventListener('click', function (e) {
                    e.stopPropagation();
                    var open = (profileDropdown.style.display === 'block');
                    hideAll();
                    profileDropdown.style.display = open ? 'none' : 'block';
                });
            }

            document.addEventListener('click', hideAll);
        })();
    </script>
</body>
</html>