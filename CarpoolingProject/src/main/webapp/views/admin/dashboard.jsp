<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - RideShare</title>
    <!-- Reuse your passenger css or create adminDashboard.css with same tokens -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/passengerDashboard.css">
    <style>
      /* small admin-specific tweaks */
      .section { margin-top: 22px; }
      .section-header { display:flex; align-items:center; justify-content:space-between; margin-bottom:12px; }
      .filters { display:flex; flex-wrap:wrap; gap:8px; }
      .filters input, .filters select { padding:8px; border:1px solid #ddd; border-radius:6px; }
      .table-wrap { overflow:auto; border:1px solid #eee; border-radius:10px; }
      table { width:100%; border-collapse: collapse; }
      th, td { padding:12px 10px; border-bottom:1px solid #eee; text-align:left; white-space:nowrap; }
      thead th { background:#fafafa; font-weight:600; }
      .tag { font-size:12px; padding:2px 8px; border-radius:12px; background:#eef2ff; display:inline-block; }
      .tag.ok { background:#e8f6ee; color:#1b7b4d; }
      .tag.warn { background:#fff4e5; color:#b76a00; }
      .tag.danger { background:#ffecec; color:#c0362b; }
      .actions a { margin-right:10px; }
      .grid { display:grid; grid-template-columns: repeat(3, minmax(0, 1fr)); gap:16px; }
      @media (max-width: 900px){ .grid{ grid-template-columns:1fr; } }
      .stat-card { display:flex; gap:12px; align-items:center; padding:16px; border:1px solid #eee; border-radius:12px; background:#fff; }
      .stat-icon { width:40px; height:40px; display:grid; place-items:center; border-radius:10px; background:#f4f6ff; }
      .stat-value { font-weight:700; font-size:20px; }
      .muted { color:#888; }
      .btn-small { padding:6px 10px; font-size:13px; border:1px solid #ddd; border-radius:6px; text-decoration:none; }
      .btn-danger { border-color:#ffb3ae; color:#b6190e; }
      .btn-outline { color:#333; }
      .btn-primary { background:#1f76ff; color:#fff; border:1px solid #1f76ff; }
      .search-row { display:flex; gap:8px; align-items:center; }
      .right { text-align:right; }
      .subtle { font-size:12px; color:#666; }
      .pill { font-size:12px; padding:2px 8px; border-radius:999px; border:1px solid #e6e6e6; }
    </style>
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

            <!-- User Menu (copy of passenger header) -->
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
                    <div id="profileDropdown" class="profile-dropdown" style="display:none;">
                        <a href="${pageContext.request.contextPath}/profile" class="dropdown-item">Profile</a>
                        <a href="${pageContext.request.contextPath}/logout" class="dropdown-item">Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <c:if test="${not empty param.flash}">
	  <div class="alert success" style="margin-bottom:12px;">${param.flash}</div>
	</c:if>

    <!-- Main -->
    <main class="dashboard-main">
        <div class="container">
            <!-- KPIs -->
            <div class="grid">
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- users -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><circle cx="9" cy="7" r="4"/><path d="M17 21v-2a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4v2"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${totalUsers}</div>
                        <div class="subtle">Total Users</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- trips -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 17h2v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2V17"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${totalTrips}</div>
                        <div class="subtle">Total Trips</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- bookings -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${totalBookings}</div>
                        <div class="subtle">Total Bookings</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- feedback -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${totalFeedbacks}</div>
                        <div class="subtle">Feedbacks</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- earnings -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="1" x2="12" y2="23"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${totalEarnings}</div>
                        <div class="subtle">Total Earnings</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <!-- cancellations -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
                    </div>
                    <div>
                        <div class="stat-value">${cancellationRate}%</div>
                        <div class="subtle">Cancellation Rate</div>
                    </div>
                </div>
            </div>

            <!-- Users -->
			<div class="section" id="users">
			  <div class="section-header">
			    <h2>Users</h2>
			  </div>
			
			  <div class="table-wrap">
			    <table>
			      <thead>
			        <tr>
			          <th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Role</th><th>Joined</th><th>Actions</th>
			        </tr>
			      </thead>
			      <tbody>
			        <c:choose>
			          <c:when test="${not empty users}">
			            <c:forEach items="${users}" var="u">
			              <tr>
			                <td>${u.id}</td>
			                <td><c:out value="${u.firstName}"/> <c:out value="${u.lastName}"/></td>
			                <td><c:out value="${u.email}"/></td>
			                <td><c:out value="${u.phoneNumber}"/></td>
			                <td><span class="tag"><c:out value="${u.role}"/></span></td>
			                <td><c:out value="${u.createdAt}"/></td>
			                  <td class="actions">
								  <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="btn-small btn-outline">Edit</a>
								
								  <form method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline;">
								    <input type="hidden" name="action" value="delete"/>
								    <input type="hidden" name="id" value="${u.id}"/>
								    <button class="btn-small btn-danger"
								            onclick="return confirm('Delete user #${u.id}? This may cascade and remove trips/bookings/feedback!');">
								      Delete
								    </button>
								  </form>
			                	</td>
			              	</tr>
			            </c:forEach>
			          </c:when>
			          <c:otherwise>
			            <tr><td colspan="7" class="subtle">No users found.</td></tr>
			          </c:otherwise>
			        </c:choose>
			      </tbody>
			    </table>
			  </div>
			
			  <div class="right subtle" style="margin-top:6px;">
			    <c:set var="startIdx" value="${(u_page - 1) * u_pageSize + 1}"/>
			    <c:set var="endIdx" value="${(u_page - 1) * u_pageSize + fn:length(users)}"/>
			    Showing ${startIdx}–${endIdx} of ${totalUsers}
			  </div>
			
			  <div class="right" style="margin-top:10px; display:flex; gap:8px; justify-content:flex-end;">
			    <c:if test="${u_page > 1}">
			      <a class="btn-small btn-outline" href="${pageContext.request.contextPath}/admin/dashboard?u_page=${u_page-1}#users">Prev</a>
			    </c:if>
			
			    <c:if test="${u_hasNext}">
			      <a class="btn-small" href="${pageContext.request.contextPath}/admin/dashboard?u_page=${u_page+1}#users">Next</a>
			    </c:if>
			  </div>
			</div>


            <!-- Trips -->
			<div class="section">
			  <div class="section-header">
			    <h2>Trips</h2>
			
			    <form method="get" action="${pageContext.request.contextPath}/admin/dashboard" class="search-row">
			      <select name="t_status">
			        <option value="" ${empty t_status ? 'selected' : ''}>Status: All</option>
			        <option value="UPCOMING" ${t_status == 'UPCOMING' ? 'selected' : ''}>UPCOMING</option>
			        <option value="PASSED"   ${t_status == 'PASSED'   ? 'selected' : ''}>PASSED</option>
			        <option value="COMPLETED" ${t_status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
			      </select>
			      <input type="date" name="t_date" value="${t_date}"/>
			
			      <!-- Keep users tab pagination if you have it -->
			      <input type="hidden" name="u_page" value="${u_page}"/>
			
			      <button class="btn-small btn-outline" type="submit">Filter</button>
			      <a class="btn-small" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
			    </form>
			  </div>
			
			  <div class="table-wrap">
			    <table>
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
			              <td>${t.dateTime}</td>
			              <td>${t.price} MAD</td>
			              <td>${t.seatsAvailable}</td>
			              <td>${t.driverId}</td>
			              <td>
			                <c:set var="st" value="${tripStatus[t.id]}"/>
			                <span class="tag ${st == 'UPCOMING' ? 'ok' : (st == 'PASSED' ? 'warn' : '')}">${st}</span>
			              </td>
			              <td class="actions">
							  <!-- View -->
							  <a href="${pageContext.request.contextPath}/admin/trips?action=view&id=${t.id}"
							     class="btn-small btn-outline">View</a>
							
							  <!-- Edit -->
							  <a href="${pageContext.request.contextPath}/admin/trips?action=edit&id=${t.id}"
							     class="btn-small btn-outline">Edit</a>
							
							  <!-- Delete -->
							  <form method="post" action="${pageContext.request.contextPath}/admin/trips" style="display:inline;">
							    <input type="hidden" name="action" value="delete"/>
							    <input type="hidden" name="id" value="${t.id}"/>
							    <input type="hidden" name="ret"
							           value="${pageContext.request.requestURI}?t_status=${t_status}&t_date=${t_date}&t_page=${t_page}"/>
							    <button class="btn-small btn-danger"
							            onclick="return confirm('Delete trip #${t.id}?');">Delete</button>
							  </form>
							
							  <!-- Force Complete only when status is PASSED -->
							  <c:if test="${tripStatus[t.id] == 'PASSED'}">
							    <form method="post" action="${pageContext.request.contextPath}/admin/trips" style="display:inline;">
							      <input type="hidden" name="action" value="forceComplete"/>
							      <input type="hidden" name="id" value="${t.id}"/>
							      <input type="hidden" name="ret"
							             value="${pageContext.request.requestURI}?t_status=${t_status}&t_date=${t_date}&t_page=${t_page}"/>
							      <button class="btn-small btn-outline"
							              onclick="return confirm('Force trip #${t.id} to COMPLETED?');">
							        Force Complete
							      </button>
							    </form>
							  </c:if>
							</td>
			            </tr>
			          </c:forEach>
			        </c:when>
			        <c:otherwise>
			          <tr><td colspan="8" class="muted">No trips found.</td></tr>
			        </c:otherwise>
			      </c:choose>
			      </tbody>
			    </table>
			  </div>
			  <c:if test="${t_total > 0}">
				  <div class="right subtle" style="margin-top:6px;">
				    <c:set var="fromRow" value="${(t_page-1)*t_pageSize + 1}"/>
			        <c:set var="toRow"   value="${(t_page-1)*t_pageSize + fn:length(trips)}"/>
				    Showing ${fromRow}–${toRow} of ${t_total}
				  </div>
				
				  <div class="right" style="margin-top:10px; display:flex; gap:8px; justify-content:flex-end;">
				    <c:if test="${t_page > 1}">
			          <a class="btn-small btn-outline"
			             href="${pageContext.request.contextPath}/admin/dashboard?t_status=${t_status}&t_date=${t_date}&t_page=${t_page-1}">Prev</a>
			        </c:if>
			        <c:if test="${t_hasNext}">
			          <a class="btn-small btn-outline"
			             href="${pageContext.request.contextPath}/admin/dashboard?t_status=${t_status}&t_date=${t_date}&t_page=${t_page+1}">Next</a>
			        </c:if>
				  </div>
			  </c:if>
			</div>

            <!-- BOOKINGS -->
			<div class="section">
			  <div class="section-header">
			    <h2>Bookings</h2>
			    <form method="get" action="${pageContext.request.contextPath}/admin/dashboard" class="search-row" style="display:flex; gap:8px; align-items:center;">
			      <select name="b_status">
			        <option value="" ${empty b_status ? 'selected' : ''}>Status: All</option>
			        <option value="PENDING"   ${b_status == 'PENDING'   ? 'selected' : ''}>PENDING</option>
			        <option value="CONFIRMED" ${b_status == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
			        <option value="CANCELLED" ${b_status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
			        <option value="COMPLETED" ${b_status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
			      </select>
			      <input type="text" name="b_tripId" placeholder="Trip ID" value="${b_tripId}"/>
			      <input type="text" name="b_passengerId" placeholder="Passenger ID" value="${b_passengerId}"/>
			      <button class="btn-small btn-outline" type="submit">Filter</button>
			      <a class="btn-small" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
			    </form>
			  </div>
			
			  <div class="table-wrap">
			    <table>
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
			                  ${t.departureCity} &rarr; ${t.arrivalCity}
			                </c:if>
			              </td>
			              <td>
			                <c:if test="${not empty t}">${t.dateTime}</c:if>
			              </td>
			              <td>${bookingPassengerNameMap[b.passengerId]}</td>
			              <td>
			                <c:choose>
			                  <c:when test="${b.status == 'PENDING'}"><span class="pill warn">PENDING</span></c:when>
			                  <c:when test="${b.status == 'CONFIRMED'}"><span class="pill ok">CONFIRMED</span></c:when>
			                  <c:when test="${b.status == 'CANCELLED'}"><span class="pill">CANCELLED</span></c:when>
			                  <c:when test="${b.status == 'COMPLETED'}"><span class="pill">COMPLETED</span></c:when>
			                  <c:otherwise><span class="pill">${b.status}</span></c:otherwise>
			                </c:choose>
			              </td>
			              <td class="actions">
			                <!-- PENDING: confirm, cancel, delete -->
			                <c:if test="${b.status == 'PENDING'}">
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="confirm"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <!-- preserve filters -->
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-outline">Confirm</button>
			                  </form>
			
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="cancel"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-outline">Cancel</button>
			                  </form>
			
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="delete"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-danger" onclick="return confirm('Delete booking #${b.id}?');">Delete</button>
			                  </form>
			                </c:if>
			
			                <!-- CONFIRMED: cancel, delete -->
			                <c:if test="${b.status == 'CONFIRMED'}">
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="cancel"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-outline">Cancel</button>
			                  </form>
			
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="delete"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-danger" onclick="return confirm('Delete booking #${b.id}?');">Delete</button>
			                  </form>
			                </c:if>
			
			                <!-- COMPLETED / CANCELLED: delete -->
			                <c:if test="${b.status == 'COMPLETED' || b.status == 'CANCELLED'}">
			                  <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
			                    <input type="hidden" name="action" value="delete"/>
			                    <input type="hidden" name="id" value="${b.id}"/>
			                    <input type="hidden" name="b_status" value="${b_status}"/>
			                    <input type="hidden" name="b_tripId" value="${b_tripId}"/>
			                    <input type="hidden" name="b_passengerId" value="${b_passengerId}"/>
			                    <input type="hidden" name="b_page" value="${b_page}"/>
			                    <button class="btn-small btn-danger" onclick="return confirm('Delete booking #${b.id}?');">Delete</button>
			                  </form>
			                </c:if>
			              </td>
			            </tr>
			          </c:forEach>
			        </c:when>
			        <c:otherwise>
			          <tr><td colspan="6" class="muted">No bookings found.</td></tr>
			        </c:otherwise>
			      </c:choose>
			      </tbody>
			    </table>
			  </div>
			
			  <c:if test="${b_total > 0}">
			    <div class="right subtle" style="margin-top:6px;">
			      <c:set var="b_fromRow" value="${(b_page-1)*b_pageSize + 1}"/>
			      <c:set var="b_toRow"   value="${(b_page-1)*b_pageSize + fn:length(bookings)}"/>
			      Showing ${b_fromRow}–${b_toRow} of ${b_total}
			    </div>
			
			    <div class="right" style="margin-top:10px; display:flex; gap:8px; justify-content:flex-end;">
			      <c:if test="${b_page > 1}">
			        <a class="btn-small btn-outline"
			           href="${pageContext.request.contextPath}/admin/dashboard?b_status=${b_status}&b_tripId=${b_tripId}&b_passengerId=${b_passengerId}&b_page=${b_page-1}">Prev</a>
			      </c:if>
			      <c:if test="${b_hasNext}">
			        <a class="btn-small btn-outline"
			           href="${pageContext.request.contextPath}/admin/dashboard?b_status=${b_status}&b_tripId=${b_tripId}&b_passengerId=${b_passengerId}&b_page=${b_page+1}">Next</a>
			      </c:if>
			    </div>
			  </c:if>
			</div>

            <!-- Feedbacks -->
			<div class="section">
			  <div class="section-header">
			    <h2>Feedbacks</h2>
			
			    <!-- Filters -->
			    <form class="search-row" method="get" action="${pageContext.request.contextPath}/admin/dashboard">
			      <select name="f_type">
			        <option value="" ${empty f_type ? 'selected' : ''}>Type: All</option>
			        <option value="DRIVER"   ${f_type == 'DRIVER'   ? 'selected' : ''}>DRIVER</option>
			        <option value="PASSENGER"${f_type == 'PASSENGER'? 'selected' : ''}>PASSENGER</option>
			      </select>
			
			      <input type="number" name="f_author" min="1" placeholder="Author userId"
			             value="${f_author != null ? f_author : ''}" />
			      <input type="number" name="f_target" min="1" placeholder="Target userId"
			             value="${f_target != null ? f_target : ''}" />
			      <input type="number" name="f_min" min="1" max="5" placeholder="Min rating"
			             value="${f_min != null ? f_min : ''}" />
			
			      <button class="btn-small btn-outline" type="submit">Filter</button>
			      <a class="btn-small" href="${pageContext.request.contextPath}/admin/dashboard">Reset</a>
			    </form>
			  </div>
			
			  <div class="table-wrap">
			    <table>
			      <thead>
			      <tr>
			        <th>ID</th><th>Rating</th><th>Comment</th><th>Author</th>
			        <th>Target</th><th>Type</th><th>When</th><th>Actions</th>
			      </tr>
			      </thead>
			      <tbody>
			      <c:choose>
			        <c:when test="${not empty feedbacks}">
			          <c:forEach items="${feedbacks}" var="f">
			            <tr>
			              <td>${f.id}</td>
			              <td>${f.rating}/5</td>
			              <td><c:out value="${f.comment}" /></td>
			              <td><c:out value="${authorNames[f.authorId]}" /></td>
			              <td><c:out value="${targetNames[f.targetId]}" /></td>
			              <td><span class="pill"><c:out value="${f.feedbackType}" /></span></td>
			              <td>${f.dateTime}</td> <!-- already LocalDateTime; you can format in servlet if needed -->
			
			              <td class="actions">
			                <form method="post" action="${pageContext.request.contextPath}/admin/feedbacks" style="display:inline;">
							  <input type="hidden" name="action" value="delete"/>
							  <input type="hidden" name="id" value="${f.id}"/>
							  <button class="btn-small btn-danger"
							          onclick="return confirm('Delete feedback #${f.id}?');">
							    Delete
							  </button>
							</form>
			              </td>
			            </tr>
			          </c:forEach>
			        </c:when>
			        <c:otherwise>
			          <tr><td colspan="8" class="subtle">No feedbacks found.</td></tr>
			        </c:otherwise>
			      </c:choose>
			      </tbody>
			    </table>
			  </div>
			
			    <c:if test="${f_total > 0}">
			  <div class="right subtle" style="margin-top:6px;">
			    <c:set var="f_fromRow" value="${(f_page-1)*f_pageSize + 1}"/>
			    <c:set var="f_toRow"   value="${(f_page-1)*f_pageSize + fn:length(feedbacks)}"/>
			    Showing ${f_fromRow}–${f_toRow} of ${f_total}
			  </div>
			
			  <div class="right" style="margin-top:10px; display:flex; gap:8px; justify-content:flex-end;">
			    <c:if test="${f_page > 1}">
			      <c:url var="prevF" value="/admin/dashboard">
			        <c:param name="f_page" value="${f_page - 1}"/>
			        <c:if test="${not empty f_type}"><c:param name="f_type" value="${f_type}"/></c:if>
			        <c:if test="${f_author != null}"><c:param name="f_author" value="${f_author}"/></c:if>
			        <c:if test="${f_target != null}"><c:param name="f_target" value="${f_target}"/></c:if>
			        <c:if test="${f_min != null}"><c:param name="f_min" value="${f_min}"/></c:if>
			      </c:url>
			      <a class="btn-small btn-outline" href="${prevF}">Prev</a>
			    </c:if>
			
			    <c:if test="${f_hasNext}">
			      <c:url var="nextF" value="/admin/dashboard">
			        <c:param name="f_page" value="${f_page + 1}"/>
			        <c:if test="${not empty f_type}"><c:param name="f_type" value="${f_type}"/></c:if>
			        <c:if test="${f_author != null}"><c:param name="f_author" value="${f_author}"/></c:if>
			        <c:if test="${f_target != null}"><c:param name="f_target" value="${f_target}"/></c:if>
			        <c:if test="${f_min != null}"><c:param name="f_min" value="${f_min}"/></c:if>
			      </c:url>
			      <a class="btn-small btn-outline" href="${nextF}">Next</a>
			    </c:if>
			  </div>
			</c:if>
        </div>
    </main>
</div>

<script>
  (function () {
    var notifBell = document.getElementById('notificationBell');
    var notifDropdown = document.getElementById('notificationDropdown');
    var profileBtn = document.getElementById('profileButton');
    var profileDropdown = document.getElementById('profileDropdown');

    function hideAll(){
      if (notifDropdown) notifDropdown.style.display = 'none';
      if (profileDropdown) profileDropdown.style.display = 'none';
    }
    if (notifBell){
      notifBell.addEventListener('click', function(e){
        e.stopPropagation();
        var open = notifDropdown.style.display === 'block';
        hideAll();
        notifDropdown.style.display = open ? 'none' : 'block';
      });
    }
    if (profileBtn){
      profileBtn.addEventListener('click', function(e){
        e.stopPropagation();
        var open = profileDropdown.style.display === 'block';
        hideAll();
        profileDropdown.style.display = open ? 'none' : 'block';
      });
    }
    document.addEventListener('click', hideAll);
  })();
</script>
</body>
</html>
