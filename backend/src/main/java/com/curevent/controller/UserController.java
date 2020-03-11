package com.curevent.controller;

import com.curevent.model.User;
import com.curevent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).stream()
                .findAny().orElse(null); //if not found return null
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
