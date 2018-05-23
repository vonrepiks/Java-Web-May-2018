package org.softuni.casebook.controllers;

import org.softuni.casebook.annotations.Controller;
import org.softuni.casebook.annotations.GetMapping;
import org.softuni.javache.http.HttpResponse;

@Controller
public class UserController extends BaseController {
    @GetMapping(route = "/login")
    public byte[] login(HttpResponse httpResponse) {
        return super.processPageRequest("/login", httpResponse);
    }

    @GetMapping(route = "/register")
    public byte[] register(HttpResponse httpResponse) {
        return super.processPageRequest("/register", httpResponse);
    }
}
