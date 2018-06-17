package org.softuni.app.controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.broccolina.solet.HttpSoletResponse;
import org.softuni.summer.api.Controller;
import org.softuni.summer.api.GetMapping;
import org.softuni.summer.api.Model;
import org.softuni.summer.api.PathVariable;

@Controller
public class HomeController {
    @GetMapping(route = "/")
    public String index() {
        return "Home index";
    }

    @GetMapping(route = "/profile")
    public String profile() {
        return "template:profile";
    }

    @GetMapping(route = "/test/{id}/test/{testId}")
    public String test(HttpSoletRequest httpSoletRequest, @PathVariable(name = "id") String id, HttpSoletResponse httpSoletResponse, @PathVariable(name = "testId") String testId, Model model) {
        model.addAttribute("url", httpSoletRequest.getRequestUrl());
        model.addAttribute("id", id);
        model.addAttribute("testId", testId);

        return "template:test";
    }
}
