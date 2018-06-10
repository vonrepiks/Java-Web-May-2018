package org.softuni.fdmc.servlets.orders;

import org.softuni.fdmc.database.entities.Order;
import org.softuni.fdmc.database.entities.User;
import org.softuni.fdmc.database.repositories.OrderRepository;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.softuni.fdmc.Constants.ADMIN_ROLE;

@WebServlet("/orders/all")
public class OrdersAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/users/login");
            return;
        }

        UserRepository userRepository = (UserRepository) this.getServletContext().getAttribute("userRepository");
        String username = (String) req.getSession().getAttribute("username");
        User user = userRepository.findByUsername(username);

        if (!ADMIN_ROLE.equals(user.getRole())) {
            resp.sendRedirect("/");
            return;
        }

        OrderRepository orderRepository = (OrderRepository) this.getServletContext().getAttribute("orderRepository");

        List<Order> orders = orderRepository.findAllByClientId(user.getId());

        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/WEB-INF/jsp/orders/all.jsp").forward(req, resp);
    }
}
