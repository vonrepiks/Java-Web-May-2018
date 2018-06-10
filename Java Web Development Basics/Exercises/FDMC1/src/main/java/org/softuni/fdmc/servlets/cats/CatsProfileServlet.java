package org.softuni.fdmc.servlets.cats;

import org.softuni.fdmc.database.entities.Cat;
import org.softuni.fdmc.database.repositories.CatRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/profile")
public class CatsProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/users/login");
            return;
        }

        CatRepository catRepository = (CatRepository) this.getServletContext().getAttribute("catRepository");

        String catName = req.getParameter("catName");

        Cat cat = catRepository.findByName(catName);

        cat.setViews(cat.getViews() + 1);

        catRepository.update(cat);

        req.getRequestDispatcher("/WEB-INF/jsp/cats/profile.jsp").forward(req, resp);
    }
}
