package servlets.product;

import models.Product;
import models.ProductType;
import repositories.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/create")
public class ProductCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().setAttribute("wrongProductType", "");
        req.getRequestDispatcher("/products/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Product product = new Product();

        product.setName(req.getParameter("name"));
        product.setDescription(req.getParameter("description"));
        product.setProductType(ProductType.valueOf(req.getParameter("type").toUpperCase()));

        ProductRepository productRepository = (ProductRepository) this.getServletContext().getAttribute("productRepository");

        productRepository.addProduct(product);

        resp.sendRedirect("/products/all");
    }
}
