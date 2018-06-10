<%--@elvariable id="username" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fluffy Duffy Munchkin Cats </title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/styles/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <c:import url="templates/header.jsp"/>
    <h1 class="mt-5">Welcome to Fluffy Duffy Munchkin Cats!</h1>
    <c:choose>
        <c:when test='${username == null}'>
            <h3>Login if you have an account, or Register if you don't!</h3>
        </c:when>
        <c:otherwise>
            <h3>Navigate through the application using the links below!</h3>
            <hr />
            <a href="<c:url value="/orders/all"/>" role="button" class="btn btn-primary mt-3" >All Orders</a>
        </c:otherwise>
    </c:choose>
    <hr/>
</div>
</body>
</html>
