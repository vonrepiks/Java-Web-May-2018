package org.softuni.casebook.controllers.dynamic;

import org.softuni.casebook.annotations.Controller;
import org.softuni.casebook.annotations.GetMapping;
import org.softuni.casebook.annotations.PostMapping;
import org.softuni.casebook.constants.CasebookConstants;
import org.softuni.casebook.constants.ErrorMessages;
import org.softuni.casebook.constants.SuccessMessages;
import org.softuni.casebook.constants.WarningMessages;
import org.softuni.casebook.template_engine.LimeLeafImpl;
import org.softuni.casebook.utility.Notification;
import org.softuni.database.entities.User;
import org.softuni.database.repositories.BaseRepository;
import org.softuni.database.repositories.UserRepositoryImpl;
import org.softuni.javache.http.*;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.Map;

@Controller
public class UserController extends DynamicBaseController {
    private BaseRepository userRepository;

    public UserController(HttpSessionStorage sessionStorage, LimeLeafImpl limeLeaf, Notification notification) {
        super(sessionStorage, limeLeaf, notification);
        this.userRepository = new UserRepositoryImpl();
    }

    @GetMapping(route = "/login")
    public byte[] loginGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (super.isLoggedIn(httpRequest)) {
            super.getLimeLeaf().addAttributeToViewData("warning", WarningMessages.RESTRICT_ACCESS_FOR_LOGGED_IN_USERS_MESSAGE);
            return super.redirect("home", httpRequest, httpResponse);
        }
        byte[] loginViewData = super.view("login", httpRequest, httpResponse);
        super.getLimeLeaf().clearNotificationsMessages();
        return super.ok(loginViewData, httpResponse);
    }

    @PostMapping(route = "/login")
    public byte[] loginPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyParameters = httpRequest.getBodyParameters();

        if (bodyParameters.size() != 2) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.ALL_FIELDS_REQUIRED);
            byte[] loginViewData = super.view("login", httpRequest, httpResponse);
            return super.badRequest(loginViewData, httpResponse);
        }

        String email = bodyParameters.get("email");
        String password = bodyParameters.get("password");

        User user;
        try {
            user = this.userRepository.getUserByEmail(email);
        } catch (NoResultException nre) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.NO_USER_WITH_THIS_EMAIL);
            byte[] loginViewData = super.view("login", httpRequest, httpResponse);
            return super.badRequest(loginViewData, httpResponse);
        }

        if (!user.getPassword().equals(password)) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.WRONG_PASSWORD);
            byte[] loginViewData = super.view("login", httpRequest, httpResponse);
            return super.badRequest(loginViewData, httpResponse);
        }

        HttpSession session = new HttpSessionImpl();
        session.addAttribute("user-id", user.getId());

        super.getSessionStorage().addSession(session);

        httpResponse.addCookie("Javache", session.getId());

        byte[] homeViewData = super.view("home", httpRequest, httpResponse);

        super.getLimeLeaf().addAttributeToViewData("success", SuccessMessages.SUCCESSFULLY_LOGIN);
        return super.redirect(homeViewData, "/home", httpResponse);
    }

    @GetMapping(route = "/register")
    public byte[] registerGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (super.isLoggedIn(httpRequest)) {
            super.getLimeLeaf().addAttributeToViewData("warning", WarningMessages.RESTRICT_ACCESS_FOR_LOGGED_IN_USERS_MESSAGE);
            return super.redirect("home", httpRequest, httpResponse);
        }
        byte[] registerViewData = super.view("register", httpRequest, httpResponse);
        return super.ok(registerViewData, httpResponse);
    }

    @PostMapping(route = "/register")
    public byte[] registerPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyParameters = httpRequest.getBodyParameters();

        if (bodyParameters.size() != 3) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.ALL_FIELDS_REQUIRED);
            byte[] registerViewData = super.view("register", httpRequest, httpResponse);
            return super.badRequest(registerViewData, httpResponse);
        }

        String email = bodyParameters.get("email");
        String password = bodyParameters.get("password");
        String confirmPassword = bodyParameters.get("confirmPassword");

        if (!password.equals(confirmPassword)) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.PASSWORDS_MISMATCH_ERROR_MESSAGE);
            byte[] registerViewData = super.view("register", httpRequest, httpResponse);
            return super.badRequest(registerViewData, httpResponse);
        }

        User user = new User(email, password);

        try {
            this.userRepository.create(user);
        } catch (RollbackException re) {
            super.getLimeLeaf().addAttributeToViewData("error", ErrorMessages.USER_ALREADY_EXIST_ERROR_MESSAGE);
            byte[] registerViewData = super.view("register", httpRequest, httpResponse);
            return super.badRequest(registerViewData, httpResponse);
        }

        byte[] loginViewData = super.view("login", httpRequest, httpResponse);

        super.getLimeLeaf().addAttributeToViewData("success", SuccessMessages.SUCCESSFULLY_REGISTER);
        return super.redirect(loginViewData, "/login", httpResponse);
    }

    @PostMapping(route = "/logout")
    public byte[] logout(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!this.isLoggedIn(httpRequest)) {
            return this.redirect("login", httpRequest, httpResponse);
        }

        HttpCookie cookie = httpRequest.getCookies().get(CasebookConstants.SERVER_SESSION_TOKEN);
        HttpSession currentSession = super.getSessionStorage().getById(cookie.getValue());
        currentSession.invalidate();
        super.getSessionStorage().refreshSessions();

        httpResponse.addCookie("Javache", "token=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");

        super.getLimeLeaf().addAttributeToViewData("success", SuccessMessages.SUCCESSFULLY_LOGOUT);
        return this.redirect("", httpRequest, httpResponse);
    }

    @GetMapping(route = "/users/profile")
    public byte[] profile(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!super.isLoggedIn(httpRequest)) {
            super.getLimeLeaf().addAttributeToViewData("warning", WarningMessages.RESTRICT_ACCESS_FOR_GUEST_USERS_MESSAGE);
            return super.redirect("login", httpRequest, httpResponse);
        }

        User user = super.getCurrentUser(httpRequest, this.userRepository);

        super.getLimeLeaf().addAttributeToViewData("email", user.getEmail());
        super.getLimeLeaf().addAttributeToViewData("password", user.getPassword());

        byte[] profileViewData = super.view("users/profile", httpRequest, httpResponse);

        return super.ok(profileViewData, httpResponse);
    }
}
