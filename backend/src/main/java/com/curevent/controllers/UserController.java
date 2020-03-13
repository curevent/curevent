package com.curevent.controllers;

import com.curevent.models.User;
import com.curevent.services.UserService;
import com.curevent.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.getOneById(id);
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody @Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        if (!result.hasErrors()) {
            userService.add(user);
        }
    }
}
