<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gérer les Conducteurs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <%@ include file="adminHeader.jsp" %>
            
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Gérer les Conducteurs</h1>
                    <a href="addDriver.jsp" class="btn btn-primary">Ajouter Conducteur</a>
                </div>

                <c:if test="${not empty param.success}">
                    <div class="alert alert-success">${param.success}</div>
                </c:if>
                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger">${param.error}</div>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Email</th>
                                <th>Téléphone</th>
                                <th>Permis</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="driver" items="${drivers}">
                                <tr>
                                    <td>${driver.id}</td>
                                    <td>${driver.userId}</td> <!-- Vous devrez récupérer les infos user -->
                                    <td>${driver.licenceNumber}</td>
                                    <td>
                                        <a href="drivers?action=edit&id=${driver.id}" class="btn btn-sm btn-warning">Modifier</a>
                                        <a href="drivers?action=delete&id=${driver.id}" class="btn btn-sm btn-danger" 
                                           onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</body>
</html>