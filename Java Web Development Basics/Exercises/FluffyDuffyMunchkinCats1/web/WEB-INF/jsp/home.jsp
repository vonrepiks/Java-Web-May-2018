<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fluffy Duffy Munchkin Cats </title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/styles/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />" type="image/x-icon" />
</head>
<body>
<div class="container mt-3">
    <h1>Welcome to Fluffy Duffy Munchkin Cats!</h1>
    <h3>Navigate through the application using the links below!</h3>
    <hr/>
    <div class="mt-5">
        <a href="<c:url value="/cats/create"/>">Create cat</a>
        <div>
    </div>
        <a href="<c:url value="/cats/all"/>">All cats</a>
    </div>
</div>
</body>
</html>
