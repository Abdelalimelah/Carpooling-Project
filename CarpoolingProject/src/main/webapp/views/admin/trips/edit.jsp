<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Edit Trip #${trip.id}</title></head>
<body>
  <a href="${ret}" class="btn">← Back</a>
  <h1>Edit Trip #${trip.id}</h1>

  <form method="post" action="${pageContext.request.contextPath}/admin/trips">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${trip.id}"/>
    <input type="hidden" name="ret" value="${ret}"/>

    <div>
      <label>Departure City</label>
      <input name="departureCity" value="${trip.departureCity}" required/>
    </div>
    <div>
      <label>Arrival City</label>
      <input name="arrivalCity" value="${trip.arrivalCity}" required/>
    </div>
    <div>
      <label>Date/Time</label>
      <!-- HTML datetime-local uses T separator -->
      <input type="datetime-local" name="dateTime"
             value="${trip.dateTime.toString().replace(' ', 'T')}"/>
    </div>
    <div>
      <label>Price (MAD)</label>
      <input type="number" step="0.01" name="price" value="${trip.price}" required/>
    </div>
    <div>
      <label>Seats Available</label>
      <input type="number" min="0" max="8" name="seatsAvailable" value="${trip.seatsAvailable}" required/>
    </div>
    <div>
      <label>Car ID</label>
      <input type="number" name="carId" value="${trip.carId}" required/>
    </div>

    <button type="submit">Save</button>
  </form>
</body>
</html>