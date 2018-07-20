package controllers;

import entities.*;
import models.binding.ProductCreateBindingModel;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.*;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import utility.DecimalFormatter;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
public class ProductController extends BaseController {
    private UserRepository userRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public ProductController() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
    }

    @GetMapping(route = "/products/details/{id}")
    public String details(@PathVariable(name = "id") String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        Product foundProduct = this.productRepository.findById(id);

        if (foundProduct == null) {
            return super.redirect("home");
        }

        String adminActions = "";

        if (super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            adminActions = new StringBuilder()
                    .append("<a class=\"btn chushka-bg-color\" href=\"/products/edit/" + foundProduct.getId() + "\">Edit</a>")
                    .append("<a class=\"btn chushka-bg-color\" href=\"/products/delete/" + foundProduct.getId() + "\">Delete</a>")
                    .toString();

        }

        model.addAttribute("id", foundProduct.getId());
        model.addAttribute("name", foundProduct.getName());
        model.addAttribute("price", DecimalFormatter.getDecimalFormat().format(foundProduct.getPrice()));
        model.addAttribute("type", foundProduct.getType().toString());
        model.addAttribute("description", foundProduct.getDescription());
        model.addAttribute("adminActions", adminActions);

        return super.view("product-details");
    }

    @GetMapping(route = "/products/order/{id}")
    public String order(@PathVariable(name = "id") String id, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        String currentUserId = request.getSession().getAttributes().get("user-id").toString();

        User currentUser = this.userRepository.findById(currentUserId);
        Product currentProduct = this.productRepository.findById(id);

        if (currentUser == null || currentProduct == null) {
            return super.redirect("home");
        }

        Order order = new Order();

        order.setClient(currentUser);
        order.setProduct(currentProduct);
        order.setOrderedOn(LocalDateTime.now());

        this.orderRepository.createOrder(order);

        return super.redirect("home");
    }

    @GetMapping(route = "/products/create")
    public String createProduct(HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        return super.view("product-create");
    }

    @PostMapping(route = "/products/create")
    public String createProductConfirm(ProductCreateBindingModel productCreateBindingModel, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        Product product = new Product();

        product.setName(productCreateBindingModel.getName());
        product.setDescription(productCreateBindingModel.getDescription());
        product.setPrice(productCreateBindingModel.getPrice());
        product.setType(ProductType.valueOf(productCreateBindingModel.getType().toUpperCase()));

        this.productRepository.createProduct(product);

        return super.redirect("home");
    }

    @GetMapping(route = "/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        Product product = this.productRepository.findById(id);

        if (product == null) {
            return super.redirect("home");
        }

        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("price", DecimalFormatter.getDecimalFormat().format(product.getPrice()));
        model.addAttribute("description", product.getDescription());
        model.addAttribute("foodChecked", product.getType().equals(ProductType.FOOD) ? "checked" : "");
        model.addAttribute("domesticChecked", product.getType().equals(ProductType.DOMESTIC) ? "checked" : "");
        model.addAttribute("healthChecked", product.getType().equals(ProductType.HEALTH) ? "checked" : "");
        model.addAttribute("cosmeticChecked", product.getType().equals(ProductType.COSMETIC) ? "checked" : "");
        model.addAttribute("otherChecked", product.getType().equals(ProductType.OTHER) ? "checked" : "");

        return super.view("product-delete");
    }

    @PostMapping(route = "/products/delete/{id}")
    public String deleteProductConfirm(@PathVariable(name = "id") String id, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        Product product = this.productRepository.findById(id);
        Set<Order> orders = product.getOrders();

        orders.forEach(order -> System.out.println(order.getId()));

        for (Order order : orders) {
            this.orderRepository.removeById(order.getId());
            System.out.println();
        }

        this.productRepository.removeProductById(id);

        return super.redirect("home");
    }

    @GetMapping(route = "/products/edit/{id}")
    public String editProduct(@PathVariable(name = "id") String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        Product product = this.productRepository.findById(id);

        if (product == null) {
            return super.redirect("home");
        }

        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("price", DecimalFormatter.getDecimalFormat().format(product.getPrice()));
        model.addAttribute("description", product.getDescription());
        model.addAttribute("foodChecked", product.getType().equals(ProductType.FOOD) ? "checked" : "");
        model.addAttribute("domesticChecked", product.getType().equals(ProductType.DOMESTIC) ? "checked" : "");
        model.addAttribute("healthChecked", product.getType().equals(ProductType.HEALTH) ? "checked" : "");
        model.addAttribute("cosmeticChecked", product.getType().equals(ProductType.COSMETIC) ? "checked" : "");
        model.addAttribute("otherChecked", product.getType().equals(ProductType.OTHER) ? "checked" : "");

        return super.view("product-edit");
    }

    @PostMapping(route = "/products/edit/{id}")
    public String editProductConfirm(@PathVariable(name = "id") String id, ProductCreateBindingModel productCreateBindingModel, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        Product product = this.productRepository.findById(id);

        if (product == null) {
            return super.redirect("home");
        }

        product.setName(productCreateBindingModel.getName());
        product.setDescription(productCreateBindingModel.getDescription());
        product.setPrice(productCreateBindingModel.getPrice());
        product.setType(ProductType.valueOf(productCreateBindingModel.getType().toUpperCase()));

        this.productRepository.updateProduct(product);

        return super.redirect("home");
    }
}
