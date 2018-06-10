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
    <c:import url="../templates/header.jsp"/>
    <h1 class="mt-3">All Cats</h1>
    <hr/>
    <c:set var = "user" scope = "request" value = "${applicationScope.userRepository.findByUsername(username)}"/>
    <c:set var = "cats" scope = "request" value = "${applicationScope.catRepository.findAllByCreatorIdSortedByViews(user.getId())}"/>
    <c:choose>
        <c:when test='${cats.size() == 0}'>
            <c:choose>
                <c:when test='${applicationScope.userRepository.findByUsername(username).getRole().equals("ADMIN")}'>
                    <h2>There are no cats.<a href="<c:url value="/cats/create"/>">Create some!</a></h2>
                </c:when>
                <c:otherwise>
                    <h2>There are no cats.</h2>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:forEach items="${cats}" var="cat">
                <h3>
                    <a href="<c:url value="/cats/profile?catName=${cat.getName()}"/>">${cat.getName()}</a>
                </h3>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <div class="mt-5">
        <a href="<c:url value="/"/>">Back To Home</a>
    </div>
</div>
</body>
</html>
