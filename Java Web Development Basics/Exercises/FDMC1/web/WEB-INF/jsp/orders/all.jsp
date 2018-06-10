<%--@elvariable id="orders" type="java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fluffy Duffy Munchkin Cats </title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/styles/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/styles/styles.css" />" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <c:import url="../templates/header.jsp"/>
    <h1 class="mt-3">All Orders</h1>
    <hr/>
    <div class="orders">
        <c:forEach items="${orders}" var="order">
            <div class="order">
                <div class="row">
                    <h2 class="col-sm-7">Client username: </h2>
                    <h2 class="col-sm-5">${order.getClient().getUsername()}</h2>
                </div>
                <div class="row">
                    <h2 class="col-sm-7">Cat name: </h2>
                    <h2 class="col-sm-5">${order.getCat().getName()}</h2>
                </div>
                <div class="row">
                    <h2 class="col-sm-7">Date of order: </h2>
                    <h2 class="col-sm-5">${order.getMadeOn().toString().replace("T", " ")}</h2>
                </div>
            </div>
        </c:forEach>
    </div>
    <hr/>
    <div class="mt-5">
        <a href="<c:url value="/"/>">Back To Home</a>
    </div>
</div>
</body>
</html>
