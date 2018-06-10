package org.softuni.fdmc.servlets.cats;

import org.softuni.fdmc.database.entities.Cat;
import org.softuni.fdmc.database.entities.User;
import org.softuni.fdmc.database.repositories.CatRepository;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.softuni.fdmc.Constants.ADMIN_ROLE;

@WebServlet("/cats/create")
public class CatsCreateServlet extends HttpServlet {

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
        req.getRequestDispatcher("/WEB-INF/jsp/cats/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String breed = req.getParameter("breed");
        String color = req.getParameter("color");
        Integer numberOfLegs = Integer.parseInt(req.getParameter("legs"));

        UserRepository userRepository = (UserRepository) this.getServletContext().getAttribute("userRepository");

        User creator = userRepository.findByUsername(req.getSession().getAttribute("username").toString());

        Cat cat = new Cat(name, breed, color, numberOfLegs, creator);

        CatRepository catRepository = (CatRepository) this.getServletContext().getAttribute("catRepository");

        catRepository.create(cat);

        resp.sendRedirect("/cats/profile?catName=" + cat.getName());
    }
}
