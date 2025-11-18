<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Trip #${trip.id}</title></head>
<body>
  <a href="${ret}" class="btn">← Back</a>
  <h1>Trip #${trip.id}</h1>

  <p><b>Route:</b> ${trip.departureCity} → ${trip.arrivalCity}</p>
  <p><b>Date/Time:</b> ${trip.dateTime}</p>
  <p><b>Price:</b> ${trip.price} MAD</p>
  <p><b>Seats:</b> ${trip.seatsAvailable}</p>

  <c:if test="${driverUser != null}">
    <p><b>Driver:</b> ${driverUser.firstName} ${driverUser.lastName} (${driverUser.email})</p>
  </c:if>
  <c:if test="${car != null}">
    <p><b>Car:</b> ${car.brand} ${car.model} • ${car.plateNumber}</p>
  </c:if>

  <h3>Bookings</h3>
  <c:choose>
    <c:when test="${not empty bookings}">
      <table border="1" cellspacing="0" cellpadding="6">
        <thead><tr><th>ID</th><th>Passenger ID</th><th>Status</th><th>Booked At</th></tr></thead>
        <tbody>
          <c:forEach items="${bookings}" var="b">
            <tr>
              <td>${b.id}</td>
              <td>${b.passengerId}</td>
              <td>${b.status}</td>
              <td>${b.bookingDate}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>No bookings.</c:otherwise>
  </c:choose>
</body>
</html>