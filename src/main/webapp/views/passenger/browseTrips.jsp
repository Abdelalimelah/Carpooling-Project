<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse Trips - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/browseTrips.css">
</head>
<body>
    <div class="trips-wrapper">
        <!-- Header -->
        <header class="trips-header">
            <div class="header-content">
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
        <main class="trips-main">
            <div class="container">
                <!-- Page Header -->
                <div class="page-header">
                    <h1>Browse Available Trips</h1>
                    <p>Find the perfect ride for your journey</p>
                </div>

                <!-- Filters Section -->
                <div class="filters-section">
                    <div class="section-header">
                        <h2>Filter Trips</h2>
                    </div>
                    <form method="get" class="filters-form">
                        <div class="filter-row">
                            <div class="form-group">
                                <label for="departureCity">From</label>
                                <div class="input-wrapper">
                                    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <circle cx="12" cy="10" r="3"/>
                                        <path d="M12 21.7C17.3 17 20 13 20 10a8 8 0 1 0-16 0c0 3 2.7 6.9 8 11.7z"/>
                                    </svg>
                                    <input type="text" id="departureCity" name="departureCity" value="${departureCity}" placeholder="Casablanca">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="arrivalCity">To</label>
                                <div class="input-wrapper">
                                    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <circle cx="12" cy="10" r="3"/>
                                        <path d="M12 21.7C17.3 17 20 13 20 10a8 8 0 1 0-16 0c0 3 2.7 6.9 8 11.7z"/>
                                    </svg>
                                    <input type="text" id="arrivalCity" name="arrivalCity" value="${arrivalCity}" placeholder="Marrakech">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="date">Date</label>
                                <div class="input-wrapper">
                                    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                        <line x1="16" y1="2" x2="16" y2="6"/>
                                        <line x1="8" y1="2" x2="8" y2="6"/>
                                        <line x1="3" y1="10" x2="21" y2="10"/>
                                    </svg>
                                    <input type="date" id="date" name="date" value="${date}">
                                </div>
                            </div>
                        </div>

                        <div class="filter-actions">
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <circle cx="11" cy="11" r="8"/>
                                    <line x1="21" y1="21" x2="16.65" y2="16.65"/>
                                </svg>
                                Search Trips
                            </button>
                            <a href="${pageContext.request.contextPath}/passenger/trips" class="btn btn-outline">
                                Clear Filters
                            </a>
                        </div>
                    </form>
                </div>

                <!-- Results Section -->
                <div class="results-section">
                    <div class="section-header">
                        <h2>Available Trips</h2>
                        <c:if test="${not empty trips}">
                            <span class="results-count">${fn:length(trips)} trip(s) found</span>
                        </c:if>
                    </div>

                    <c:choose>
                        <c:when test="${not empty trips}">
                            <div class="trips-grid">
                                <c:forEach items="${trips}" var="trip">
                                    <c:set var="car" value="${carMap[trip.id]}" />
                                    <c:set var="driverUser" value="${driverUserMap[trip.id]}" />
                                    <c:set var="seatsLeft" value="${seatsLeftMap[trip.id]}" />
                                    
                                    <div class="trip-card">
                                        <div class="trip-header">
                                            <div class="route">
                                                <span class="departure">${trip.departureCity}</span>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="5" y1="12" x2="19" y2="12"/>
                                                    <polyline points="12 5 19 12 12 19"/>
                                                </svg>
                                                <span class="arrival">${trip.arrivalCity}</span>
                                            </div>
                                            <div class="seats-indicator ${seatsLeft > 0 ? 'available' : 'unavailable'}">
                                                <c:choose>
                                                    <c:when test="${seatsLeft > 0}">
                                                        ${seatsLeft} seat(s) left
                                                    </c:when>
                                                    <c:otherwise>
                                                        No seats available
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="trip-details">
                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                                    <line x1="8" y1="2" x2="8" y2="6"/>
                                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                                </svg>
                                                <span>${trip.dateTime}</span>
                                            </div>

                                            <div class="detail-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="12" y1="1" x2="12" y2="23"/>
                                                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                                                </svg>
                                                <span>${trip.price} MAD</span>
                                            </div>

                                            <c:if test="${car != null}">
                                                <div class="detail-item">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path d="M5 17h2v2H5v-2zm12 0h2v2h-2v-2z"/>
                                                        <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99z"/>
                                                    </svg>
                                                    <span>${car.brand} ${car.model} (${car.plateNumber})</span>
                                                </div>
                                            </c:if>

                                            <c:if test="${driverUser != null}">
                                                <div class="detail-item">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                                        <circle cx="12" cy="7" r="4"/>
                                                    </svg>
                                                    <span>${driverUser.firstName} ${driverUser.lastName}</span>
                                                </div>
                                            </c:if>
                                        </div>

                                        <div class="trip-actions">
                                            <c:choose>
                                                <c:when test="${seatsLeft > 0}">
                                                    <a href="${pageContext.request.contextPath}/passenger/bookings/create?tripId=${trip.id}" class="btn btn-primary">
                                                        Book Now
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-disabled" disabled>
                                                        Fully Booked
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="${pageContext.request.contextPath}/passenger/trips/details?id=${trip.id}" class="btn btn-outline">
                                                View Details
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                                    <circle cx="7" cy="17" r="2"/>
                                    <path d="M9 17h6"/>
                                    <circle cx="17" cy="17" r="2"/>
                                </svg>
                                <h3>No Trips Found</h3>
                                <p>No available trips match your search criteria. Try adjusting your filters.</p>
                                <a href="${pageContext.request.contextPath}/passenger/trips" class="btn btn-primary">Clear Filters</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/browseTrips.js"></script>
</body>
</html>