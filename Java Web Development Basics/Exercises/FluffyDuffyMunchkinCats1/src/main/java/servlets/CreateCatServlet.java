package servlets;

import models.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/cats/create")
public class CreateCatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/createCat.jsp").forward(req, resp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String breed = req.getParameter("breed");
        String color = req.getParameter("color");
        int legs = Integer.parseInt(req.getParameter("legs"));

        Cat cat = new Cat(name, breed, color, legs);

        Map<String, Cat> cats = (Map<String, Cat>) this.getServletConfig().getServletContext().getAttribute("cats");

        if (cats == null) {
            cats = new HashMap<>();
        }

        cats.putIfAbsent(name, cat);

        this.getServletConfig().getServletContext().setAttribute("cats", cats);

        resp.sendRedirect("/cats/profile?catName=" + cats.get(name).getName());
    }
}
