package servlets;

import data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cats/profile")
public class CatsProfileServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Cat> catsMap = (Map<String, Cat>) this.getServletConfig().getServletContext().getAttribute("cats");

        if (catsMap == null) {
            catsMap = new HashMap<>();
            this.getServletConfig().getServletContext().setAttribute("cats", catsMap);
        }

        String queryString = req.getQueryString();

        String name = queryString.split("=")[1];

        Cat neededCat = catsMap.get(name);

        req.setAttribute("cat", neededCat);
        req.setAttribute("catName", name);

        req.getRequestDispatcher("/WEB-INF/jsp/catsProfile.jsp").forward(req, resp);
    }
}
