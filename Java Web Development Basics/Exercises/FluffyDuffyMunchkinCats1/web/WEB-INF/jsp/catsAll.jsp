<%--@elvariable id="cats" type="java.util.List"--%>
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
    <h1>All Cats</h1>
    <hr/>
    <c:choose>
        <c:when test="${cats.size() == 0}">
            <h2>There are no cats.<a href="<c:url value="/cats/create"/>">Create some!</a></h2>
        </c:when>
        <c:otherwise>
            <c:forEach items="${cats}" var="cat">
                <h3>
                    <a href="<c:url value="/cats/profile?name=${cat.name}"/>">${cat.name}</a>
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
