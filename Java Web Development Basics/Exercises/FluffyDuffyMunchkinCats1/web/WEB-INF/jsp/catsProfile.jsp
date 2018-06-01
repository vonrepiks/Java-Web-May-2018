<%--@elvariable id="cat" type="Cat.class"--%>
<%--@elvariable id="catName" type="java.lang.String"--%>
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
<div class="container mt-3">
    <c:choose>
        <c:when test="${cat == null}">
            <h1>Cat, with name - ${catName} was not found.</h1>
        </c:when>
        <c:otherwise>
            <h1>Cat - ${cat.getName()}</h1>
            <hr/>
            <h2>Breed: ${cat.getBreed()}</h2>
            <h2>Color: ${cat.getColor()}</h2>
            <h2>Number of legs: ${cat.getNumberOfLegs()}</h2>
            <hr/>
        </c:otherwise>
    </c:choose>
    <div class="mt-5">
        <a href="<c:url value="/cats/all"/>">Back</a>
    </div>
</div>
</body>
</html>
