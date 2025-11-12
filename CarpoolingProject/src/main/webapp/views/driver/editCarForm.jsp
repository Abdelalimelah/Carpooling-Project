<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Car - RideShare</title>
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
                    <h1>Edit Car</h1>
                    <p>Update your car information</p>
                </div>

                <div class="form-section">
                    <c:if test="${not empty errorMessage}">
                        <div class="message error">
                            <c:out value="${errorMessage}" />
                        </div>
                    </c:if>

                    <form class="car-form" action="edit" method="post">
                        <input type="hidden" name="id" value="${car.id}">
                        
                        <div class="form-group">
                            <label for="brand">Brand</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                    <path d="M6 9h4.5"/>
                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                    <path d="M6 12h2"/>
                                    <circle cx="16" cy="16" r="1.5"/>
                                    <circle cx="8.5" cy="16" r="1.5"/>
                                </svg>
                                <input type="text" id="brand" name="brand" value="${car.brand}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="model">Model</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M14 16H9m10 0h3v-3.15a1 1 0 0 0-.84-.99L16 11l-2.7-3.6a1 1 0 0 0-.8-.4H8.5a2 2 0 0 0-2 2v6.75a1 1 0 0 0 1 1Z"/>
                                    <path d="M6 9h4.5"/>
                                    <path d="M6 9v7.5A1.5 1.5 0 0 0 7.5 18H9"/>
                                    <path d="M6 12h2"/>
                                    <circle cx="16" cy="16" r="1.5"/>
                                    <circle cx="8.5" cy="16" r="1.5"/>
                                </svg>
                                <input type="text" id="model" name="model" value="${car.model}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="plateNumber">Plate Number</label>
                            <div class="input-wrapper">
                                <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect width="18" height="18" x="3" y="3" rx="2"/>
                                    <path d="M7 7h10"/>
                                    <path d="M7 11h10"/>
                                    <path d="M7 15h4"/>
                                </svg>
                                <input type="text" id="plateNumber" name="plateNumber" value="${car.plateNumber}" required>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                                </svg>
                                Update Car
                            </button>
                            <a href="${pageContext.request.contextPath}/driver/dashboard" class="btn btn-outline">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</body>
</html>