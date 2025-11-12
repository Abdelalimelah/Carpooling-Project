<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Car - RideShare</title>
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
                    <h1>Add New Car</h1>
                    <p>Register your car to start publishing trips</p>
                </div>

                <div class="form-section">
                    <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="message error"><%= request.getAttribute("errorMessage") %></div>
                    <% } %>

                    <form class="car-form" action="add" method="post">
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
                                <input type="text" id="brand" name="brand" placeholder="Example : Mercedes-Benz, Toyota, Volkswagen" required>
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
                                <input type="text" id="model" name="model" placeholder="Example : C-Class, Camry, Golf " required>
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
                                <input type="text" id="plateNumber" name="plateNumber" placeholder="Example : ABC123 " required>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 5v14M5 12h14"/>
                                </svg>
                                Add Car
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</body>
</html>