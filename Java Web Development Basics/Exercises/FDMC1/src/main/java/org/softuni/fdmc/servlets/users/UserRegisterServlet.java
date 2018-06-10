package org.softuni.fdmc.servlets.users;

import org.softuni.fdmc.database.entities.User;
import org.softuni.fdmc.database.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/register")
public class UserRegisterServlet extends HttpServlet {
    private static final String USER_ROLE = "USER";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/users/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if(!password.equals(confirmPassword)) {
            resp.sendRedirect("/users/register");
            return;
        }

        User user = new User(username, password);

        user.setRole(USER_ROLE);

        UserRepository userRepository = (UserRepository) this.getServletContext().getAttribute("userRepository");

        userRepository.create(user);

        resp.sendRedirect("/users/login");
    }
}
