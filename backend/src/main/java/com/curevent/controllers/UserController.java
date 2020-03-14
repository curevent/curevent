package com.curevent.controllers;

import com.curevent.exceptions.UserAlreadyExistsException;
import com.curevent.exceptions.UserNotFoundException;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.UserService;
import com.curevent.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public UserTransfer getUser(@PathVariable UUID id) {
        UserEntity userEntity = userService.getOneById(id);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        UserTransfer userTransfer = UserTransfer.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .country(userEntity.getCountry())
                .city(userEntity.getCity())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .build();
        return userTransfer;
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody @Valid UserTransfer userTransfer, BindingResult result) {
        UserEntity user = UserEntity.builder()
                .username(userTransfer.getUsername())
                .email(userTransfer.getEmail())
                .name(userTransfer.getName())
                .surname(userTransfer.getSurname())
                .country(userTransfer.getCountry())
                .city(userTransfer.getCity())
                .password(userTransfer.getPassword())
                .status(null)
                .build();

        userValidator.validate(user, result);
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            throw new UserAlreadyExistsException(fieldError.getField());
        }
        userService.add(user);
    }
}
