package servlets;

import data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/cats/all")
public class CatsAllServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Cat> catsMap = (Map<String, Cat>) this.getServletConfig().getServletContext().getAttribute("cats");

        if (catsMap == null) {
            catsMap = new HashMap<>();
            this.getServletConfig().getServletContext().setAttribute("cats", catsMap);
        }

        Collection<Cat> cats = catsMap.values();

        req.setAttribute("cats", cats);

        req.getRequestDispatcher("/WEB-INF/jsp/catsAll.jsp").forward(req, resp);
    }
}
