package org.softuni.fdmc.servlets.users;

import org.softuni.fdmc.database.entities.User;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserRepository userRepository = (UserRepository)
                this.getServletContext()
                .getAttribute("userRepository");

        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect("/users/login");
            return;
        }

        req.getSession().setAttribute("username", user.getUsername());

        resp.sendRedirect("/");
    }
}
