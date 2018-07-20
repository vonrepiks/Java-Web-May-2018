package org.softuni.controllers;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.users.LoginUserDto;
import org.softuni.dtos.users.RegisterUserDto;
import org.softuni.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute RegisterUserDto registerUserDto) {
        return super.view("views/users/register", registerUserDto);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute RegisterUserDto registerUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("views/users/register", registerUserDto);
        }
        this.userService.registerUser(registerUserDto);

        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute LoginUserDto loginUserDto) {
        return super.view("views/users/login", loginUserDto);
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@Valid @ModelAttribute LoginUserDto loginUserDto) {
        return super.redirect("/");
    }

    @GetMapping("/logout")
    @PreAuthenticate(loggedIn = true)
    public ModelAndView logout(HttpSession httpSession) {
        return super.redirect("/");
    }
}
