package org.softuni.fdmc.servlets.home;

import org.softuni.fdmc.database.repositories.CatRepository;
import org.softuni.fdmc.database.repositories.OrderRepository;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        this.getServletContext().setAttribute("catRepository", new CatRepository());
        this.getServletContext().setAttribute("userRepository", new UserRepository());
        this.getServletContext().setAttribute("orderRepository", new OrderRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
