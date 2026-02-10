<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/driverForms.css">
</head>
<body>
    <div class="form-wrapper">
        <header class="form-header">
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
                    <a href="${pageContext.request.contextPath}/driver/dashboard" class="btn btn-outline">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 12H5M12 19l-7-7 7-7"/>
                        </svg>
                        Back to Dashboard
                    </a>
                </nav>
            </div>
        </header>

        <main class="form-main">
            <div class="container">
                <div class="page-header">
                    <h1>Trip Details</h1>
                    <p>Manage bookings and trip information</p>
                </div>

                <!-- Trip Information -->
                <div class="section">
                    <div class="trip-info-card">
                        <div class="trip-header">
                            <div class="route">
                                <span class="departure">${trip.departureCity}</span>
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <line x1="5" y1="12" x2="19" y2="12"/>
                                    <polyline points="12 5 19 12 12 19"/>
                                </svg>
                                <span class="arrival">${trip.arrivalCity}</span>
                            </div>
                            <div class="trip-price">${trip.price} MAD</div>
                        </div>
                        
                        <div class="trip-details-grid">
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
                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                    <path d="M6 9h4.5"/>
                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                    <path d="M6 12h2"/>
                                    <circle cx="16" cy="16" r="1.5"/>
                                    <circle cx="8.5" cy="16" r="1.5"/>
                                </svg>
                                <span>${car.brand} ${car.model} (${car.plateNumber})</span>
                            </div>
                            
                            <div class="detail-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                    <circle cx="9" cy="7" r="4"/>
                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                                    <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                                </svg>
                                <span>${trip.seatsAvailable} total seats</span>
                            </div>
                            
                            <div class="detail-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                    <circle cx="9" cy="7" r="4"/>
                                </svg>
                                <span>
                                    <c:choose>
                                        <c:when test="${seatsLeft > 0}">
                                            <span style="color: var(--success);">${seatsLeft} seats left</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: var(--danger);">No seats left</span>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Bookings Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Bookings (${bookings.size()})</h2>
                    </div>

                    <c:choose>
                        <c:when test="${not empty bookings}">
                            <div class="bookings-list">
                                <c:forEach items="${bookings}" var="booking">
                                    <c:set var="passengerUser" value="${passengerUserMap[booking.passengerId]}" />
                                    <div class="booking-card">
                                        <div class="booking-header">
                                            <div class="passenger-info">
                                                <div class="passenger-avatar">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                                        <circle cx="12" cy="7" r="4"/>
                                                    </svg>
                                                </div>
                                                <div class="passenger-details">
                                                    <h4>
                                                        <c:if test="${passengerUser != null}">
                                                            ${passengerUser.firstName} ${passengerUser.lastName}
                                                        </c:if>
                                                    </h4>
                                                    <span class="booking-date">${booking.bookingDate}</span>
                                                </div>
                                            </div>
                                            <div class="booking-status ${booking.status.toLowerCase()}">
                                                <c:choose>
                                                    <c:when test="${booking.status == 'PENDING'}">Pending</c:when>
                                                    <c:when test="${booking.status == 'CONFIRMED'}">Confirmed</c:when>
                                                    <c:when test="${booking.status == 'CANCELLED'}">Cancelled</c:when>
                                                    <c:when test="${booking.status == 'COMPLETED'}">Completed</c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                        
                                        <div class="booking-actions">
                                            <c:choose>
                                                <c:when test="${booking.status == 'COMPLETED'}">
                                                    <a href="${pageContext.request.contextPath}/feedback?tripId=${trip.id}&targetId=${passengerUser.id}&type=PASSENGER" 
                                                       class="btn btn-sm btn-outline">
                                                        Rate Passenger
                                                    </a>
                                                </c:when>
                                                <c:when test="${booking.status == 'PENDING'}">
                                                    <a href="${pageContext.request.contextPath}/driver/bookings/confirm?id=${booking.id}&tripId=${trip.id}" 
                                                       class="btn btn-sm btn-success">Confirm</a>
                                                    <a href="${pageContext.request.contextPath}/driver/bookings/cancel?id=${booking.id}&tripId=${trip.id}" 
                                                       class="btn btn-sm btn-danger">Cancel</a>
                                                </c:when>
                                                <c:when test="${booking.status == 'CONFIRMED'}">
                                                    <a href="${pageContext.request.contextPath}/driver/bookings/cancel?id=${booking.id}&tripId=${trip.id}" 
                                                       class="btn btn-sm btn-danger">Cancel</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="no-actions">—</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                    <circle cx="9" cy="7" r="4"/>
                                </svg>
                                <h3>No Bookings Yet</h3>
                                <p>No passengers have booked this trip yet.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>
</body>
</html>