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
    <h1 class="mb-5 mt-3 text-center">Create a Cat!</h1>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <form method="post">
                <div class="form-group row">
                    <label for="name" class="col-sm-3">Name</label>
                    <input type="text" class="form-control col-sm-9" id="name" name="name" placeholder="Enter name">
                </div>
                <div class="form-group row">
                    <label for="breed" class="col-sm-3">Breed</label>
                    <input type="text" class="form-control col-sm-9" id="breed" name="breed" placeholder="Enter breed">
                </div>
                <div class="form-group row">
                    <label for="color" class="col-sm-3">Color</label>
                    <input type="text" class="form-control col-sm-9" id="color" name="color" placeholder="Enter color">
                </div>
                <div class="form-group row">
                    <label for="legs" class="col-sm-3">Number of legs</label>
                    <input type="number" class="form-control col-sm-9" id="legs" name="legs"
                           placeholder="Enter number of legs">
                </div>
                <div class="text-center mt-3">
                    <button type="submit" class="btn btn-primary">Create cat</button>
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
