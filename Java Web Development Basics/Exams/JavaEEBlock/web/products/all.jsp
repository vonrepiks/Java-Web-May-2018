<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ProductRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <h1>All products</h1>
</header>
<main>
    <%
        ProductRepository productsRepo = (ProductRepository) application.getAttribute("productRepository");
        List<Product> products = productsRepo.getProducts();
    %>
    <% for (Product product : products) { %>
    <h3>
        <a href="/products/details?name=<%= product.getName() %>"><%= product.getName() %>
        </a>
    </h3>
    <% } %>
</main>
</body>
</html>
