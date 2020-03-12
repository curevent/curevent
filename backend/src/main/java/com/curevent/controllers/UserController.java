package com.curevent.controllers;

import com.curevent.models.User;
import com.curevent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable UUID id) {

        return userService.getOneById(id);
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user) {
        userService.add(user);
    }
}
