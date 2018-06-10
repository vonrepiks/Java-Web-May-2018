<%--@elvariable id="cat" type="Cat.class"--%>
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
    <c:set var = "cat" scope = "request" value = "${applicationScope.catRepository.findByName(param.catName)}"/>
    <c:choose>
        <c:when test="${cat == null}">
            <h1 class="mt-3">Cat, with name - ${param.catName} was not found.</h1>
        </c:when>
        <c:otherwise>
            <h1 class="mt-3">Cat - ${cat.getName()}</h1>
            <hr/>
            <h2>Breed: ${cat.getBreed()}</h2>
            <h2>Color: ${cat.getColor()}</h2>
            <h2>Number of legs: ${cat.getNumberOfLegs()}</h2>
            <h2>Creator: ${cat.getCreator().getUsername()}</h2>
            <hr/>
            <h2>Views: ${cat.views}</h2>
            <hr />
            <form method="post" action="<c:url value="/order?catName=${param.catName}"/>" class="mt-3">
                <button type="submit" class="btn btn-primary">Order</button>
            </form>
        </c:otherwise>
    </c:choose>
    <div class="mt-5">
        <a href="<c:url value="/cats/all"/>">Back</a>
    </div>
</div>
</body>
</html>
