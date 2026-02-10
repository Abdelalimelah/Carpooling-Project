<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Trip #${trip.id} - RideShare Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/editTrip.css">
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

                <!-- Edit Trip Card -->
                <div class="form-card">
                    <div class="form-header">
                        <h1>Edit Trip #${trip.id}</h1>
                        <p>Update trip information and manage booking details</p>
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

                    <form method="post" action="${pageContext.request.contextPath}/admin/trips" class="trip-form">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="id" value="${trip.id}"/>
                        <input type="hidden" name="ret" value="${ret}"/>

                        <div class="form-grid">
                            <!-- Route Information -->
                            <div class="form-section">
                                <h2 class="section-title">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2"/>
                                        <circle cx="7" cy="17" r="2"/>
                                        <path d="M9 17h6"/>
                                        <circle cx="17" cy="17" r="2"/>
                                    </svg>
                                    Route Information
                                </h2>
                                
                                <div class="form-group">
                                    <label for="departureCity" class="form-label">Departure City</label>
                                    <input type="text" id="departureCity" name="departureCity" 
                                           value="${trip.departureCity}" class="form-input" 
                                           placeholder="e.g., Casablanca" required/>
                                    <div class="form-hint">City where the trip starts</div>
                                </div>

                                <div class="form-group">
                                    <label for="arrivalCity" class="form-label">Arrival City</label>
                                    <input type="text" id="arrivalCity" name="arrivalCity" 
                                           value="${trip.arrivalCity}" class="form-input" 
                                           placeholder="e.g., Marrakech" required/>
                                    <div class="form-hint">City where the trip ends</div>
                                </div>
                            </div>

                            <!-- Trip Details -->
                            <div class="form-section">
                                <h2 class="section-title">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                        <line x1="16" y1="2" x2="16" y2="6"/>
                                        <line x1="8" y1="2" x2="8" y2="6"/>
                                        <line x1="3" y1="10" x2="21" y2="10"/>
                                    </svg>
                                    Trip Details
                                </h2>

                                <div class="form-group">
                                    <label for="dateTime" class="form-label">Date & Time</label>
                                    <input type="datetime-local" id="dateTime" name="dateTime"
                                           value="${trip.dateTime.toString().replace(' ', 'T')}" 
                                           class="form-input" required/>
                                    <div class="form-hint">Date and time of departure</div>
                                </div>

                                <div class="form-group">
                                    <label for="price" class="form-label">Price (MAD)</label>
                                    <input type="number" id="price" name="price" 
                                           value="${trip.price}" step="0.01" min="0"
                                           class="form-input" placeholder="0.00" required/>
                                    <div class="form-hint">Price per seat in Moroccan Dirham</div>
                                </div>

                                <div class="form-group">
                                    <label for="seatsAvailable" class="form-label">Seats Available</label>
                                    <input type="number" id="seatsAvailable" name="seatsAvailable" 
                                           value="${trip.seatsAvailable}" min="1" max="8"
                                           class="form-input" placeholder="4" required/>
                                    <div class="form-hint">Number of available seats (1-8)</div>
                                </div>
                            </div>

                            <!-- Vehicle Information -->
                            <div class="form-section">
                                <h2 class="section-title">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                        <path d="M6 9h4.5"/>
                                        <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                        <path d="M6 12h2"/>
                                        <circle cx="16" cy="16" r="1.5"/>
                                        <circle cx="8.5" cy="16" r="1.5"/>
                                    </svg>
                                    Vehicle Information
                                </h2>

                                <div class="form-group">
                                    <label for="carId" class="form-label">Car ID</label>
                                    <input type="number" id="carId" name="carId" 
                                           value="${trip.carId}" min="1"
                                           class="form-input" placeholder="123" required/>
                                    <div class="form-hint">ID of the car assigned to this trip</div>
                                </div>

                                <c:if test="${not empty cars}">
                                    <div class="car-selector">
                                        <label class="form-label">Available Cars</label>
                                        <div class="cars-list">
                                            <c:forEach items="${cars}" var="car">
                                                <div class="car-option ${car.id == trip.carId ? 'selected' : ''}" 
                                                     data-car-id="${car.id}">
                                                    <div class="car-info">
                                                        <strong>${car.brand} ${car.model}</strong>
                                                        <span class="car-plate">${car.plateNumber}</span>
                                                    </div>
                                                    <div class="car-details">
                                                        <span>ID: ${car.id}</span>
                                                        <span>Seats: ${car.seats}</span>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="form-actions">
                            <a href="${ret}" class="btn btn-outline">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 12H5M12 19l-7-7 7-7"/>
                                </svg>
                                Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
                                    <polyline points="17 21 17 13 7 13 7 21"/>
                                    <polyline points="7 3 7 8 15 8"/>
                                </svg>
                                Update Trip
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/editTrip.js"></script>
</body>
</html>