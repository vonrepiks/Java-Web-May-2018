package controllers;

import entities.Tube;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import repositories.TubeRepository;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    private TubeRepository tubeRepository;

    public HomeController() {
        this.tubeRepository = new TubeRepository();
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request, Model model) {
        if (request.getSession() != null) {
            return super.redirect(request, model, "home");
        }
        return super.view(request,model, "index");
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        if (request.getSession() == null) {
            return super.redirect(request, model, "login");
        }

        String username = request.getSession().getAttributes().get("username").toString();

        if (username == null) {
            return super.redirect(request, model, "login");
        }

        List<Tube> allTubes = this.tubeRepository.findAllTubes();

        Deque<Tube> tubes = new ArrayDeque<>();

        Collections.addAll(tubes, allTubes.toArray(new Tube[0]));

        StringBuilder tubesThumbnails = new StringBuilder();

        while (!tubes.isEmpty()) {
            tubesThumbnails.append("<div class=\"row\">");
            tubesThumbnails.append(tubes.removeFirst().extractTubeThumbnailView());
            if (tubes.isEmpty()) {
                tubesThumbnails.append("</div>");
                break;
            }
            tubesThumbnails.append(tubes.removeFirst().extractTubeThumbnailView());
            if (tubes.isEmpty()) {
                tubesThumbnails.append("</div>");
                break;
            }
            tubesThumbnails.append(tubes.removeFirst().extractTubeThumbnailView());
            tubesThumbnails.append("</div>");
        }

        model.addAttribute("username", username);
        model.addAttribute("allTubes", tubesThumbnails.toString());

        return super.view(request, model, "home");
    }
}
