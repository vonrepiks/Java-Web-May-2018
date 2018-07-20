package controllers;

import entities.Order;
import entities.Role;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import repositories.OrderRepository;
import repositories.ProductRepository;

import java.util.List;

@Controller
public class OrderController extends BaseController {

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public OrderController() {
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
    }


    @GetMapping(route = "/orders/all")
    public String allOrders(HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        if (!super.getRole(request).toString().equals(Role.ADMIN.toString())) {
            return super.redirect("login");
        }

        List<Order> allOrders = this.orderRepository.findAll();

        StringBuilder renderedOrders = new StringBuilder();

        for (int i = 0; i < allOrders.size(); i++) {
            Order currentOrder = allOrders.get(i);

            renderedOrders.append("<tr class=\"row\">");
            renderedOrders.append(currentOrder.extractOrderAllView(i));
            renderedOrders.append("</tr>");
        }

        model.addAttribute("orders", renderedOrders.toString());

        return super.view("orders-all");
    }
}
