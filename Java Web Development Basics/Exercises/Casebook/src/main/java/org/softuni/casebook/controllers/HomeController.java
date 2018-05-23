package org.softuni.casebook.controllers;

import org.softuni.casebook.annotations.Controller;
import org.softuni.casebook.annotations.GetMapping;
import org.softuni.javache.http.HttpResponse;

@Controller
public class HomeController extends BaseController {
    @GetMapping(route = "/")
    public byte[] index(HttpResponse httpResponse) {
        return super.processPageRequest("/index", httpResponse);
    }
}
