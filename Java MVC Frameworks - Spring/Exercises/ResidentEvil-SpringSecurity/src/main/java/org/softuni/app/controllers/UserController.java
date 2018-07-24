package org.softuni.app.controllers;

import org.softuni.app.factories.UserRoleFactory;
import org.softuni.app.models.dto.users.RoleServiceDto;
import org.softuni.app.models.dto.users.*;
import org.softuni.app.services.UserRoleService;
import org.softuni.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleFactory userRoleFactory, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
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

    @GetMapping("/all")
    public ModelAndView all() {
        Set<UserDto> userDtos = this.userService.findAll();
        return this.view("views/users/all", userDtos);
    }

    @PostMapping("/changeRole")
    public ModelAndView changeRole(@RequestParam("id") String id, @Valid @ModelAttribute RoleDto roleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.redirect("/users/all");
        }

        UpdateUserServiceDto updateUserServiceDto = this.userService.getUserById(id);
        if (updateUserServiceDto.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            return this.redirect("/users/all");
        }

        RoleServiceDto roleByName = userRoleService.findRoleByName(roleDto.getAuthority());
        if (updateUserServiceDto.getAuthorities().size() == 1) {
            updateUserServiceDto.getAuthorities().clear();
        }

        updateUserServiceDto.getAuthorities().add(roleByName);
        userService.update(updateUserServiceDto);
        return this.redirect("/users/all");
    }
}