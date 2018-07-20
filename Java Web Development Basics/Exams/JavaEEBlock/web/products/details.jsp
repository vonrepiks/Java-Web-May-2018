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
    <h1>Details</h1>
</header>
<main>
    <%
        ProductRepository productsRepo = (ProductRepository) application.getAttribute("productRepository");
        List<Product> products = productsRepo.getProducts();
        String name = request.getParameter("name");

        Product product = products.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    %>
    <h3><%= product.getName() %>
    </h3>
    <h3><%= product.getDescription() %>
    </h3>
    <h3><%= product.getProductType().toString() %>
    </h3>
    <hr/>
    <a href="/products/all">Back</a>
</main>
</body>
</html>
