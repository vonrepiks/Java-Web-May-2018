package tubes.servlets;

import tubes.TubesHtmlConstants;
import tubes.repositories.TubeRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class HomeTubeServlet extends HttpServlet {

    @Override
    public void init() {
        this.getServletContext().setAttribute("tubeRepo", new TubeRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(TubesHtmlConstants.HOME_TUBE_PAGE);
    }
}
