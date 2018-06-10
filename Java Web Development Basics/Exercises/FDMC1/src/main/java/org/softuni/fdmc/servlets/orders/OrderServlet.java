package org.softuni.fdmc.servlets.orders;

import org.softuni.fdmc.database.entities.Cat;
import org.softuni.fdmc.database.entities.Order;
import org.softuni.fdmc.database.entities.User;
import org.softuni.fdmc.database.repositories.CatRepository;
import org.softuni.fdmc.database.repositories.OrderRepository;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRepository userRepository = (UserRepository) this.getServletContext().getAttribute("userRepository");
        CatRepository catRepository = (CatRepository) this.getServletContext().getAttribute("catRepository");
        OrderRepository orderRepository = (OrderRepository) this.getServletContext().getAttribute("orderRepository");

        String catName = req.getParameter("catName");

        Cat cat = catRepository.findByName(catName);
        User user = userRepository.findByUsername((String) req.getSession().getAttribute("username"));

        Order order = new Order(user, cat, LocalDateTime.now());

        orderRepository.create(order);

        resp.sendRedirect("/");
    }
}
