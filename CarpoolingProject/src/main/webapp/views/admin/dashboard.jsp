<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - RideShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminDashboard.css">
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
                <!-- Flash Messages -->
                <c:if test="${not empty param.flash}">
                    <div class="alert alert-success">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                            <polyline points="22 4 12 14.01 9 11.01"/>
                        </svg>
                        <span>${param.flash}</span>
                    </div>
                </c:if>

                <!-- Welcome Section -->
                <div class="welcome-section">
                    <h1>Admin Dashboard</h1>
                    <p>Manage users, trips, bookings, and monitor platform performance.</p>
                </div>

                <!-- Stats Grid -->
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="9" cy="7" r="4"/>
                                <path d="M17 21v-2a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4v2"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${totalUsers}</span>
                            <span class="stat-label">Total Users</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M19 17h2v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2V17"/>
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
                                <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                                <line x1="16" y1="2" x2="16" y2="6"/>
                                <line x1="8" y1="2" x2="8" y2="6"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${totalBookings}</span>
                            <span class="stat-label">Total Bookings</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${totalFeedbacks}</span>
                            <span class="stat-label">Feedbacks</span>
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
                            <span class="stat-label">Total Earnings</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <line x1="18" y1="6" x2="6" y2="18"/>
                                <line x1="6" y1="6" x2="18" y2="18"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-value">${cancellationRate}%</span>
                            <span class="stat-label">Cancellation Rate</span>
                        </div>
                    </div>
                </div>

                <!-- Users Section -->
                <div class="section" id="users">
                    <div class="section-header">
                        <h2>Users Management</h2>
                    </div>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                    <th>Joined</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty users}">
                                        <c:forEach items="${users}" var="u">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td><c:out value="${u.firstName} ${u.lastName}"/></td>
                                                <td><c:out value="${u.email}"/></td>
                                                <td><c:out value="${u.phoneNumber}"/></td>
                                                <td>
                                                    <span class="status-badge status-${u.role.toLowerCase()}">
                                                        ${u.role}
                                                    </span>
                                                </td>
                                                <<td>${fn:replace(u.createdAt, 'T', ' ')}</td>
                                                <td class="actions">
                                                    <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" 
                                                       class="btn btn-sm btn-outline">Edit</a>
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/users" 
                                                          class="inline-form"
                                                          onsubmit="return confirm('Delete user #${u.id}? This may cascade and remove trips/bookings/feedback!');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="id" value="${u.id}"/>
                                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="7" class="no-data">No users found</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <div class="table-footer">
                        <div class="pagination-info">
                            <c:set var="startIdx" value="${(u_page - 1) * u_pageSize + 1}"/>
                            <c:set var="endIdx" value="${(u_page - 1) * u_pageSize + fn:length(users)}"/>
                            Showing ${startIdx}–${endIdx} of ${totalUsers}
                        </div>
                        <div class="pagination-controls">
                            <c:if test="${u_page > 1}">
                                <a class="btn btn-sm btn-outline" 
                                   href="${pageContext.request.contextPath}/admin/dashboard?u_page=${u_page-1}#users">Previous</a>
                            </c:if>
                            <c:if test="${u_hasNext}">
                                <a class="btn btn-sm btn-outline" 
                                   href="${pageContext.request.contextPath}/admin/dashboard?u_page=${u_page+1}#users">Next</a>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!-- Trips Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Trips Management</h2>
                        <form method="get" action="${pageContext.request.contextPath}/admin/dashboard" class="filters-form">
                            <div class="filters">
                                <select name="t_status" class="form-select">
                                    <option value="" ${empty t_status ? 'selected' : ''}>All Status</option>
                                    <option value="UPCOMING" ${t_status == 'UPCOMING' ? 'selected' : ''}>UPCOMING</option>
                                    <option value="PASSED" ${t_status == 'PASSED' ? 'selected' : ''}>PASSED</option>
                                    <option value="COMPLETED" ${t_status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                </select>
                                <input type="date" name="t_date" value="${t_date}" class="form-input"/>
                                <input type="hidden" name="u_page" value="${u_page}"/>
                                <button type="submit" class="btn btn-sm btn-outline">Filter</button>
                                <a class="btn btn-sm" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
                            </div>
                        </form>
                    </div>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>From → To</th>
                                    <th>Date/Time</th>
                                    <th>Price</th>
                                    <th>Seats</th>
                                    <th>Driver</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty trips}">
                                        <c:forEach items="${trips}" var="t">
                                            <tr>
                                                <td>${t.id}</td>
                                                <td>${t.departureCity} → ${t.arrivalCity}</td>
                                                <td>${fn:replace(t.dateTime, 'T', ' ')}</td>
                                                <td>${t.price} MAD</td>
                                                <td>${t.seatsAvailable}</td>
                                                <td>${t.driverId}</td>
                                                <td>
                                                    <c:set var="st" value="${tripStatus[t.id]}"/>
                                                    <span class="status-badge status-${st.toLowerCase()}">
                                                        ${st}
                                                    </span>
                                                </td>
                                                <td class="actions">
                                                    <a href="${pageContext.request.contextPath}/admin/trips?action=view&id=${t.id}"
                                                       class="btn btn-sm btn-outline">View</a>
                                                    <a href="${pageContext.request.contextPath}/admin/trips?action=edit&id=${t.id}"
                                                       class="btn btn-sm btn-outline">Edit</a>
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/trips" 
                                                          class="inline-form"
                                                          onsubmit="return confirm('Delete trip #${t.id}?');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="id" value="${t.id}"/>
                                                        <input type="hidden" name="ret"
                                                               value="${pageContext.request.requestURI}?t_status=${t_status}&t_date=${t_date}&t_page=${t_page}"/>
                                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                    </form>
                                                    <c:if test="${tripStatus[t.id] == 'PASSED'}">
                                                        <form method="post" action="${pageContext.request.contextPath}/admin/trips" 
                                                              class="inline-form"
                                                              onsubmit="return confirm('Force trip #${t.id} to COMPLETED?');">
                                                            <input type="hidden" name="action" value="forceComplete"/>
                                                            <input type="hidden" name="id" value="${t.id}"/>
                                                            <input type="hidden" name="ret"
                                                                   value="${pageContext.request.requestURI}?t_status=${t_status}&t_date=${t_date}&t_page=${t_page}"/>
                                                            <button type="submit" class="btn btn-sm btn-outline">Force Complete</button>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="8" class="no-data">No trips found</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${t_total > 0}">
                        <div class="table-footer">
                            <div class="pagination-info">
                                <c:set var="fromRow" value="${(t_page-1)*t_pageSize + 1}"/>
                                <c:set var="toRow" value="${(t_page-1)*t_pageSize + fn:length(trips)}"/>
                                Showing ${fromRow}–${toRow} of ${t_total}
                            </div>
                            <div class="pagination-controls">
                                <c:if test="${t_page > 1}">
                                    <a class="btn btn-sm btn-outline"
                                       href="${pageContext.request.contextPath}/admin/dashboard?t_status=${t_status}&t_date=${t_date}&t_page=${t_page-1}">Previous</a>
                                </c:if>
                                <c:if test="${t_hasNext}">
                                    <a class="btn btn-sm btn-outline"
                                       href="${pageContext.request.contextPath}/admin/dashboard?t_status=${t_status}&t_date=${t_date}&t_page=${t_page+1}">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                </div>

                <!-- Bookings Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Bookings Management</h2>
                        <form method="get" action="${pageContext.request.contextPath}/admin/dashboard" class="filters-form">
                            <div class="filters">
                                <select name="b_status" class="form-select">
                                    <option value="" ${empty b_status ? 'selected' : ''}>All Status</option>
                                    <option value="PENDING" ${b_status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                    <option value="CONFIRMED" ${b_status == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                                    <option value="CANCELLED" ${b_status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                    <option value="COMPLETED" ${b_status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                </select>
                                <input type="text" name="b_tripId" placeholder="Trip ID" value="${b_tripId}" class="form-input"/>
                                <input type="text" name="b_passengerId" placeholder="Passenger ID" value="${b_passengerId}" class="form-input"/>
                                <button type="submit" class="btn btn-sm btn-outline">Filter</button>
                                <a class="btn btn-sm" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
                            </div>
                        </form>
                    </div>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Trip</th>
                                    <th>Date/Time</th>
                                    <th>Passenger</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty bookings}">
                                        <c:forEach items="${bookings}" var="b">
                                            <c:set var="t" value="${bookingTripMap[b.tripId]}"/>
                                            <tr>
                                                <td>${b.id}</td>
                                                <td>
                                                    <c:if test="${not empty t}">
                                                        ${t.departureCity} → ${t.arrivalCity}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty t}">${fn:replace(t.dateTime, 'T', ' ')}</c:if>
                                                </td>
                                                <td>${bookingPassengerNameMap[b.passengerId]}</td>
                                                <td>
                                                    <span class="status-badge status-${b.status.toLowerCase()}">
                                                        ${b.status}
                                                    </span>
                                                </td>
                                                <td class="actions">
                                                    <!-- PENDING: confirm, cancel, delete -->
                                                    <c:if test="${b.status == 'PENDING'}">
                                                        <form method="post" action="${pageContext.request.contextPath}/admin/bookings" class="inline-form">
                                                            <input type="hidden" name="action" value="confirm"/>
                                                            <input type="hidden" name="id" value="${b.id}"/>
                                                            <input type="hidden" name="b_status" value="${b_status}"/>
                                                            <input type="hidden" name="b_tripId" value="${b_tripId}"/>
                                                            <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
                                                            <input type="hidden" name="b_page" value="${b_page}"/>
                                                            <button type="submit" class="btn btn-sm btn-outline">Confirm</button>
                                                        </form>
                                                        <form method="post" action="${pageContext.request.contextPath}/admin/bookings" class="inline-form">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="id" value="${b.id}"/>
                                                            <input type="hidden" name="b_status" value="${b_status}"/>
                                                            <input type="hidden" name="b_tripId" value="${b_tripId}"/>
                                                            <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
                                                            <input type="hidden" name="b_page" value="${b_page}"/>
                                                            <button type="submit" class="btn btn-sm btn-outline">Cancel</button>
                                                        </form>
                                                    </c:if>
                                                    <!-- CONFIRMED: cancel, delete -->
                                                    <c:if test="${b.status == 'CONFIRMED'}">
                                                        <form method="post" action="${pageContext.request.contextPath}/admin/bookings" class="inline-form">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="id" value="${b.id}"/>
                                                            <input type="hidden" name="b_status" value="${b_status}"/>
                                                            <input type="hidden" name="b_tripId" value="${b_tripId}"/>
                                                            <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
                                                            <input type="hidden" name="b_page" value="${b_page}"/>
                                                            <button type="submit" class="btn btn-sm btn-outline">Cancel</button>
                                                        </form>
                                                    </c:if>
                                                    <!-- All statuses: delete -->
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/bookings" 
                                                          class="inline-form"
                                                          onsubmit="return confirm('Delete booking #${b.id}?');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="id" value="${b.id}"/>
                                                        <input type="hidden" name="b_status" value="${b_status}"/>
                                                        <input type="hidden" name="b_tripId" value="${b_tripId}"/>
                                                        <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
                                                        <input type="hidden" name="b_page" value="${b_page}"/>
                                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="6" class="no-data">No bookings found</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${b_total > 0}">
                        <div class="table-footer">
                            <div class="pagination-info">
                                <c:set var="b_fromRow" value="${(b_page-1)*b_pageSize + 1}"/>
                                <c:set var="b_toRow" value="${(b_page-1)*b_pageSize + fn:length(bookings)}"/>
                                Showing ${b_fromRow}–${b_toRow} of ${b_total}
                            </div>
                            <div class="pagination-controls">
                                <c:if test="${b_page > 1}">
                                    <a class="btn btn-sm btn-outline"
                                       href="${pageContext.request.contextPath}/admin/dashboard?b_status=${b_status}&b_tripId=${b_tripId}&b_passengerId=${b_passengerId}&b_page=${b_page-1}">Previous</a>
                                </c:if>
                                <c:if test="${b_hasNext}">
                                    <a class="btn btn-sm btn-outline"
                                       href="${pageContext.request.contextPath}/admin/dashboard?b_status=${b_status}&b_tripId=${b_tripId}&b_passengerId=${b_passengerId}&b_page=${b_page+1}">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                </div>

                <!-- Feedbacks Section -->
                <div class="section">
                    <div class="section-header">
                        <h2>Feedbacks Management</h2>
                        <form method="get" action="${pageContext.request.contextPath}/admin/dashboard" class="filters-form">
                            <div class="filters">
                                <select name="f_type" class="form-select">
                                    <option value="" ${empty f_type ? 'selected' : ''}>All Types</option>
                                    <option value="DRIVER" ${f_type == 'DRIVER' ? 'selected' : ''}>DRIVER</option>
                                    <option value="PASSENGER" ${f_type == 'PASSENGER' ? 'selected' : ''}>PASSENGER</option>
                                </select>
                                <input type="number" name="f_author" min="1" placeholder="Author ID" 
                                       value="${f_author != null ? f_author : ''}" class="form-input"/>
                                <input type="number" name="f_target" min="1" placeholder="Target ID" 
                                       value="${f_target != null ? f_target : ''}" class="form-input"/>
                                <input type="number" name="f_min" min="1" max="5" placeholder="Min rating" 
                                       value="${f_min != null ? f_min : ''}" class="form-input"/>
                                <button type="submit" class="btn btn-sm btn-outline">Filter</button>
                                <a class="btn btn-sm" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
                            </div>
                        </form>
                    </div>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Rating</th>
                                    <th>Comment</th>
                                    <th>Author</th>
                                    <th>Target</th>
                                    <th>Type</th>
                                    <th>When</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty feedbacks}">
                                        <c:forEach items="${feedbacks}" var="f">
                                            <tr>
                                                <td>${f.id}</td>
                                                <td>
                                                    <div class="rating-stars-small">
                                                        <c:forEach begin="1" end="5" var="i">
                                                            <c:choose>
                                                                <c:when test="${i <= f.rating}">★</c:when>
                                                                <c:otherwise>☆</c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                        <span class="rating-value">(${f.rating}/5)</span>
                                                    </div>
                                                </td>
                                                <td class="comment-cell">
                                                    <c:out value="${fn:substring(f.comment, 0, 50)}${fn:length(f.comment) > 50 ? '...' : ''}"/>
                                                </td>
                                                <td><c:out value="${authorNames[f.authorId]}"/></td>
                                                <td><c:out value="${targetNames[f.targetId]}"/></td>
                                                <td>
                                                    <span class="status-badge status-${f.feedbackType.toLowerCase()}">
                                                        ${f.feedbackType}
                                                    </span>
                                                </td>
                                                <td>${fn:replace(f.dateTime, 'T', ' ')}</td>
                                                <td class="actions">
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/feedbacks" 
                                                          class="inline-form"
                                                          onsubmit="return confirm('Delete feedback #${f.id}?');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="id" value="${f.id}"/>
                                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="8" class="no-data">No feedbacks found</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${f_total > 0}">
                        <div class="table-footer">
                            <div class="pagination-info">
                                <c:set var="f_fromRow" value="${(f_page-1)*f_pageSize + 1}"/>
                                <c:set var="f_toRow" value="${(f_page-1)*f_pageSize + fn:length(feedbacks)}"/>
                                Showing ${f_fromRow}–${f_toRow} of ${f_total}
                            </div>
                            <div class="pagination-controls">
                                <c:if test="${f_page > 1}">
                                    <c:url var="prevF" value="/admin/dashboard">
                                        <c:param name="f_page" value="${f_page - 1}"/>
                                        <c:if test="${not empty f_type}"><c:param name="f_type" value="${f_type}"/></c:if>
                                        <c:if test="${f_author != null}"><c:param name="f_author" value="${f_author}"/></c:if>
                                        <c:if test="${f_target != null}"><c:param name="f_target" value="${f_target}"/></c:if>
                                        <c:if test="${f_min != null}"><c:param name="f_min" value="${f_min}"/></c:if>
                                    </c:url>
                                    <a class="btn btn-sm btn-outline" href="${prevF}">Previous</a>
                                </c:if>
                                <c:if test="${f_hasNext}">
                                    <c:url var="nextF" value="/admin/dashboard">
                                        <c:param name="f_page" value="${f_page + 1}"/>
                                        <c:if test="${not empty f_type}"><c:param name="f_type" value="${f_type}"/></c:if>
                                        <c:if test="${f_author != null}"><c:param name="f_author" value="${f_author}"/></c:if>
                                        <c:if test="${f_target != null}"><c:param name="f_target" value="${f_target}"/></c:if>
                                        <c:if test="${f_min != null}"><c:param name="f_min" value="${f_min}"/></c:if>
                                    </c:url>
                                    <a class="btn btn-sm btn-outline" href="${nextF}">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/adminDashboard.js"></script>
</body>
</html>