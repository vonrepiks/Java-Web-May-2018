package org.softuni.casebook.controllers.dynamic;

import org.softuni.casebook.annotations.Controller;
import org.softuni.casebook.annotations.GetMapping;
import org.softuni.database.entities.User;
import org.softuni.database.repositories.BaseRepository;
import org.softuni.database.repositories.UserRepositoryImpl;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpSessionStorage;

import java.util.List;

@Controller
public class HomeController extends DynamicBaseController {
    public HomeController(HttpSessionStorage sessionStorage) {
        super(sessionStorage);
    }

    @GetMapping(route = "/")
    public byte[] index(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (super.isLoggedIn(httpRequest)) {
            return super.redirect("home", httpRequest, httpResponse);
        }
        byte[] indexViewData = super.view("index", httpRequest, httpResponse);
        super.getLimeLeaf().addAttributeToViewData("success", "");
        return super.ok(indexViewData, httpResponse);
    }

    @GetMapping(route = "/home")
    public byte[] home(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!super.isLoggedIn(httpRequest)) {
            return super.redirect("login", httpRequest, httpResponse);
        }

        StringBuilder users = new StringBuilder();

        BaseRepository userRepository = new UserRepositoryImpl();

        List<User> allUsers = userRepository.findAll();

        User currentUser = super.getCurrentUser(httpRequest, userRepository);

        for (User user : allUsers) {
            if (currentUser.getId().equals(user.getId())) {
                continue;
            }
            users.append(String.format("<div>%s</div>", user.getEmail()));
        }

        super.getLimeLeaf().addAttributeToViewData("users", users.toString());

        byte[] homeViewData =  super.view("home", httpRequest, httpResponse);
        super.getLimeLeaf().addAttributeToViewData("success", "");
        return super.ok(homeViewData, httpResponse);
    }
}
