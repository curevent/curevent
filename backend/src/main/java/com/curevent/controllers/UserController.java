package com.curevent.controllers;

import com.curevent.exceptions.UserAlreadyExistsException;
import com.curevent.exceptions.UserNotFoundException;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.UserService;
import com.curevent.utils.mapping.UserMapper;
import com.curevent.utils.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Transactional
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator, UserMapper mapper) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.mapper = mapper;
    }


    @GetMapping("/{id}")
    public UserTransfer getUser(@PathVariable UUID id) {
        UserEntity userEntity = userService.getOneById(id);
        if (userEntity == null) {
            throw new UserNotFoundException(id);
        }
        return mapper.toTransfer(userEntity);
    }

    @GetMapping("/all")
    public List <UserTransfer> getAllUsers() {
        List <UserEntity> userEntities = userService.getAll();
        return userEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends")
    public List <UserTransfer> getUserFriends(@PathVariable UUID id) {
        List <UserEntity> userEntities = userService.getUserFriends(id);
        return userEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/")
    public UserTransfer addUser(@RequestBody @Valid UserTransfer userTransfer, BindingResult result) {
        UserEntity userEntity = mapper.toEntity(userTransfer);

        userValidator.validate(userEntity, result);
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            throw new UserAlreadyExistsException(fieldError.getField());
        }
        return mapper.toTransfer(userService.add(userEntity));
    }
}
