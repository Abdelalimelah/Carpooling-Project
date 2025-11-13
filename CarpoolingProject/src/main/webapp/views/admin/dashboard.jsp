<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
  <style>
    body{font-family:Arial,sans-serif;background:#f6f7fb;margin:0}
    .wrap{max-width:1200px;margin:0 auto;padding:24px}
    header{display:flex;justify-content:space-between;align-items:center;margin-bottom:16px}
    .tabs a{margin-right:8px;padding:8px 12px;border-radius:6px;text-decoration:none;color:#333;border:1px solid #ddd;background:#fff}
    .tabs a.active{background:#1976d2;color:#fff;border-color:#1976d2}
    .card{background:#fff;border:1px solid #e5e7eb;border-radius:8px;padding:16px;margin-top:16px}
    form.filters{display:flex;gap:10px;flex-wrap:wrap;margin-bottom:12px}
    table{width:100%;border-collapse:collapse}
    th,td{border-bottom:1px solid #eee;padding:10px;text-align:left;font-size:14px}
    th{background:#fafafa}
    .actions a{margin-right:8px}
    .muted{color:#888}
    .btn{display:inline-block;padding:6px 10px;border-radius:6px;border:1px solid #ddd;background:#fff;text-decoration:none;color:#333}
    .btn.primary{background:#1976d2;border-color:#1976d2;color:#fff}
    .danger{color:#d32f2f}
  </style>
</head>
<body>
<div class="wrap">
  <header>
    <h1>Admin Dashboard</h1>
    <a href="${pageContext.request.contextPath}/logout" class="btn">Logout</a>
  </header>

  <div class="tabs">
    <a class="${section=='users'?'active':''}"    href="?section=users">Users</a>
    <a class="${section=='trips'?'active':''}"    href="?section=trips">Trips</a>
    <a class="${section=='bookings'?'active':''}" href="?section=bookings">Bookings</a>
    <a class="${section=='feedback'?'active':''}" href="?section=feedback">Feedback</a>
  </div>

  <!-- USERS -->
  <c:if test="${section=='users'}">
    <div class="card">
      <h2>Users (lookup)</h2>
      <form class="filters" method="get">
        <input type="hidden" name="section" value="users"/>
        <input type="email" name="email" placeholder="email@example.com" value="${param.email}"/>
        <input type="number" name="userId" placeholder="User ID" value="${param.userId}" style="width:120px"/>
        <button class="btn primary" type="submit">Search</button>
      </form>

      <c:choose>
        <c:when test="${not empty users}">
          <table>
            <thead><tr><th>ID</th><th>Email</th><th>Name</th><th>Role</th><th>Created</th><th>Actions</th></tr></thead>
            <tbody>
              <c:forEach items="${users}" var="u">
                <tr>
                  <td>${u.id}</td>
                  <td>${u.email}</td>
                  <td>${u.firstName} ${u.lastName}</td>
                  <td>${u.role}</td>
                  <td><fmt:formatDate value="${u.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                  <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/users/view?id=${u.id}" class="btn">View</a>
                    <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.id}" class="btn">Edit</a>
                    <a href="${pageContext.request.contextPath}/admin/users/delete?id=${u.id}" class="btn danger" onclick="return confirm('Delete user?')">Delete</a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="muted">Provide email or userId to lookup.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </c:if>

  <!-- TRIPS (by driverId) -->
  <c:if test="${section=='trips'}">
    <div class="card">
      <h2>Trips (by driver)</h2>
      <form class="filters" method="get">
        <input type="hidden" name="section" value="trips"/>
        <input type="number" name="driverId" placeholder="Driver ID" value="${param.driverId}" style="width:140px"/>
        <button class="btn primary" type="submit">Load</button>
      </form>

      <c:choose>
        <c:when test="${not empty trips}">
          <table>
            <thead><tr><th>ID</th><th>Route</th><th>Date/Time</th><th>Price</th><th>Seats</th><th>Car</th><th>Actions</th></tr></thead>
            <tbody>
              <c:forEach items="${trips}" var="t">
                <tr>
                  <td>${t.id}</td>
                  <td>${t.departureCity} → ${t.arrivalCity}</td>
                  <td><fmt:formatDate value="${t.dateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                  <td>${t.price}</td>
                  <td>${t.seatsAvailable}</td>
                  <td>${t.carId}</td>
                  <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/trips/view?id=${t.id}" class="btn">View</a>
                    <a href="${pageContext.request.contextPath}/admin/trips/edit?id=${t.id}" class="btn">Edit</a>
                    <a href="${pageContext.request.contextPath}/admin/trips/delete?id=${t.id}" class="btn danger" onclick="return confirm('Delete trip?')">Delete</a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="muted">Enter a driverId to list trips.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </c:if>

  <!-- BOOKINGS (by tripId OR passengerId) -->
  <c:if test="${section=='bookings'}">
    <div class="card">
      <h2>Bookings</h2>
      <form class="filters" method="get">
        <input type="hidden" name="section" value="bookings"/>
        <input type="number" name="tripId" placeholder="Trip ID" value="${param.tripId}" style="width:120px"/>
        <span>or</span>
        <input type="number" name="passengerId" placeholder="Passenger ID" value="${param.passengerId}" style="width:140px"/>
        <button class="btn primary" type="submit">Load</button>
      </form>

      <c:choose>
        <c:when test="${not empty bookings}">
          <table>
            <thead><tr><th>ID</th><th>Trip</th><th>Passenger</th><th>Status</th><th>Actions</th></tr></thead>
            <tbody>
              <c:forEach items="${bookings}" var="b">
                <tr>
                  <td>${b.id}</td>
                  <td>#${b.tripId}</td>
                  <td>#${b.passengerId}</td>
                  <td>${b.status}</td>
                  <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/bookings/view?id=${b.id}" class="btn">View</a>
                    <a href="${pageContext.request.contextPath}/admin/bookings/update-status?id=${b.id}&to=CONFIRMED" class="btn">Confirm</a>
                    <a href="${pageContext.request.contextPath}/admin/bookings/update-status?id=${b.id}&to=CANCELLED" class="btn danger" onclick="return confirm('Cancel booking?')">Cancel</a>
                    <a href="${pageContext.request.contextPath}/admin/bookings/delete?id=${b.id}" class="btn danger" onclick="return confirm('Delete booking?')">Delete</a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="muted">Enter a tripId or passengerId to list bookings.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </c:if>

  <!-- FEEDBACK (by targetId & type) -->
  <c:if test="${section=='feedback'}">
    <div class="card">
      <h2>Feedback</h2>
      <form class="filters" method="get">
        <input type="hidden" name="section" value="feedback"/>
        <input type="number" name="targetId" placeholder="Target (user) ID" value="${param.targetId}" style="width:150px"/>
        <select name="type">
          <option value="">-- type --</option>
          <option value="DRIVER"   ${param.type=='DRIVER'?'selected':''}>DRIVER</option>
          <option value="PASSENGER"${param.type=='PASSENGER'?'selected':''}>PASSENGER</option>
        </select>
        <button class="btn primary" type="submit">Load</button>
      </form>

      <c:choose>
        <c:when test="${not empty feedback}">
          <table>
            <thead><tr><th>ID</th><th>Rating</th><th>Type</th><th>Author</th><th>Target</th><th>Trip</th><th>When</th><th>Comment</th><th>Actions</th></tr></thead>
            <tbody>
              <c:forEach items="${feedback}" var="f">
                <tr>
                  <td>${f.id}</td>
                  <td>${f.rating}/5</td>
                  <td>${f.feedbackType}</td>
                  <td>${f.authorId}</td>
                  <td>${f.targetId}</td>
                  <td>${f.tripId}</td>
                  <td><fmt:formatDate value="${f.dateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                  <td><c:out value="${f.comment}"/></td>
                  <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/feedback/view?id=${f.id}" class="btn">View</a>
                    <a href="${pageContext.request.contextPath}/admin/feedback/delete?id=${f.id}" class="btn danger" onclick="return confirm('Delete feedback?')">Delete</a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="muted">Enter targetId and type to list feedback.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </c:if>

  <c:if test="${not empty errorMessage}">
    <div class="card" style="border-color:#f44336;color:#b71c1c">
      ${errorMessage}
    </div>
  </c:if>
</div>
</body>
</html>
