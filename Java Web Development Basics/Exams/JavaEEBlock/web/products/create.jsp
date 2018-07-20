<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ProductRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<header>
    <h1>Create</h1>
    <hr/>
</header>
<main>
    <form method="post" action="/products/create">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" placeholder="Name..."/>
        </div>
        <hr/>
        <div>
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" placeholder="Description..."/>
        </div>
        <hr/>
        <div>
            <div style="display: inline-block">
                <input type="radio" id="food" name="type" value="food"/>
                <label for="food">Food</label>
            </div>
            <div style="display: inline-block">
                <input type="radio" id="cosmetic" name="type" value="cosmetic"/>
                <label for="cosmetic">Cosmetic</label>
            </div>
            <div style="display: inline-block">
                <input type="radio" id="health" name="type" value="health"/>
                <label for="health">Health</label>
            </div>
            <div style="display: inline-block">
                <input type="radio" id="domestic" name="type" value="domestic"/>
                <label for="domestic">Domestic</label>
            </div>
            <div style="display: inline-block">
                <input type="radio" id="other" name="type" value="other" checked/>
                <label for="other">Other</label>
            </div>
        </div>
        <hr/>
        <input type="submit" value="Create Product">
    </form>
</main>
</body>
</html>
