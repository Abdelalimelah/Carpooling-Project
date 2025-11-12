<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Error</title></head>
<body>
    <h2>Something went wrong!</h2>
    <p><b>Message:</b> <%= request.getAttribute("errorMessage") %></p>
    <p><a href="${pageContext.request.contextPath}/login">Go to Login</a></p>
</body>
</html>