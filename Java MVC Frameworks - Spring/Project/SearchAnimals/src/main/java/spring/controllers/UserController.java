package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.dtos.UserFromRegisterDto;
import spring.services.UserService;

import java.util.List;

//@RestController
public class UserController {

//    private UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "/users/all", produces = "application/json")
//    @CrossOrigin(origins = "http://localhost:3000") // TODO change me
//    public List<UserFromRegisterDto> getUsers() {
//        List<UserFromRegisterDto> allUsers = this.userService.findAll();
//
//        return allUsers;
//    }
//
//    @PostMapping(value = "/users/register", produces = "application/json")
//    @CrossOrigin(origins = "http://localhost:3000") // TODO change me
//    public String register(@RequestBody UserFromRegisterDto userdata) {
//        this.userService.createOne(userdata);
//
//        return "{ \"success\": \"true\" }";
//    }
}
