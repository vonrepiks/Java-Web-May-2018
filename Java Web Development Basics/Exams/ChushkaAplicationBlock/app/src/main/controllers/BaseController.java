package controllers;

import entities.Role;
import org.softuni.broccolina.solet.HttpSoletRequest;

public class BaseController {
    protected boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession() != null
                && request.getSession().getAttributes().containsKey("user-id");
    }

    protected Role getRole(HttpSoletRequest request) {
        return Role.valueOf(request.getSession().getAttributes().get("user-role").toString().toUpperCase());
    }

    protected String view(String view) {
        return "template:" + view;
    }

    protected String redirect(String view) {
        return "redirect:/" + view;
    }
}
