<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Edit User</title>
  <style>
    body{font-family:Arial,sans-serif; padding:20px;}
    .row{margin-bottom:12px;}
    label{display:block; font-weight:600; margin-bottom:4px;}
    input, select{padding:8px; width:320px;}
    .hint{color:#666; font-size:12px;}
    .alert{display:inline-block; padding:6px 10px; border-radius:6px; background:#f6f6f6; margin-bottom:10px;}
  </style>
</head>
<body>
  <h1>Edit User #${user.id}</h1>

  <c:if test="${not empty param.error}">
    <div class="alert" style="background:#fdecea; color:#a94442;">${param.error}</div>
  </c:if>
  <c:if test="${not empty param.success}">
    <div class="alert" style="background:#e7f7ed; color:#2d6a4f;">${param.success}</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/admin/users">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${user.id}"/>

    <div class="row">
      <label>Email</label>
      <input type="email" name="email" value="${user.email}" required/>
    </div>

    <div class="row">
      <label>First name</label>
      <input type="text" name="firstName" value="${user.firstName}"/>
    </div>

    <div class="row">
      <label>Last name</label>
      <input type="text" name="lastName" value="${user.lastName}"/>
    </div>

    <div class="row">
      <label>Phone</label>
      <input type="text" name="phoneNumber" value="${user.phoneNumber}"/>
    </div>

    <div class="row">
      <label>Role</label>
      <select id="role" name="role" required>
        <option value="PASSENGER" <c:if test="${user.role=='PASSENGER'}">selected</c:if>>PASSENGER</option>
        <option value="DRIVER"    <c:if test="${user.role=='DRIVER'}">selected</c:if>>DRIVER</option>
        <option value="ADMIN"     <c:if test="${user.role=='ADMIN'}">selected</c:if>>ADMIN</option>
      </select>
      <div class="hint">Changing role will add/remove entries in Driver/Passenger tables to stay consistent.</div>
    </div>

    <div id="licenceRow" class="row" style="display:none;">
      <label>Driver licence number</label>
      <input type="text" id="licenceNumber" name="licenceNumber" value="${licenceNumber}"/>
      <div class="hint">Required when role is DRIVER.</div>
    </div>

    <button type="submit">Update</button>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Back</a>
  </form>

  <script>
    (function(){
      var roleSel = document.getElementById('role');
      var licRow  = document.getElementById('licenceRow');
      var licInp  = document.getElementById('licenceNumber');

      function sync(){
        var isDriver = roleSel.value === 'DRIVER';
        licRow.style.display = isDriver ? 'block' : 'none';
        if (isDriver) { licInp.setAttribute('required','required'); }
        else { licInp.removeAttribute('required'); licInp.value = licInp.value; }
      }
      roleSel.addEventListener('change', sync);
      // initial
      sync();
    })();
  </script>
</body>
</html>