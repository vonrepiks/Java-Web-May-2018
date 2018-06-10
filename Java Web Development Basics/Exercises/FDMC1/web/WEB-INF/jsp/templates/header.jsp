<%--@elvariable id="username" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fluffy Duffy Munchkin Cats </title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/styles/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/styles/header.css" />" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />" type="image/x-icon"/>
</head>
<body>
<header>
    <ul class="topnav">
        <c:choose>
            <c:when test='${username == null}'>
                <li><a href="<c:url value="/"/>">Home</a></li>
                <li><a href="<c:url value="/users/login"/>">Log In</a></li>
                <li><a href="<c:url value="/users/register"/>">Register</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="<c:url value="/"/>">Home</a></li>
                <c:choose>
                <c:when test='${applicationScope.userRepository.findByUsername(username).getRole().equals("ADMIN")}'>
                    <li><a href="<c:url value="/cats/create"/>">Create Cat</a></li>
                </c:when>
                </c:choose>
                <li><a href="<c:url value="/cats/all"/>">All Cats</a></li>
                <li class="right"><a href="<c:url value="/users/logout"/>">Logout</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</header>
</body>
</html>
