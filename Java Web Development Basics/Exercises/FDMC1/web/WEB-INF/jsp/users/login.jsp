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
    <h1 class="mt-5 mb-5 text-center">Log In!</h1>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <form method="post">
                <div class="form-group row">
                    <label for="username" class="col-sm-3">Username</label>
                    <input type="text" class="form-control col-sm-9" id="username" name="username"
                           placeholder="Enter username">
                </div>
                <div class="form-group row">
                    <label for="password" class="col-sm-3">Password</label>
                    <input type="password" class="form-control col-sm-9" id="password" name="password"
                           placeholder="Enter password">
                </div>
                <div class="text-center mt-3">
                    <button type="submit" class="btn btn-primary">Log In</button>
                </div>
            </form>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <div class="mt-5">
        <a href="<c:url value="/"/>">Back To Home</a>
    </div>
</div>
</body>
</html>
