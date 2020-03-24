package com.curevent.controllers;

import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.UserService;
import com.curevent.utils.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }


    @Transactional
    @GetMapping("/{id}")
    public UserTransfer getUser(@PathVariable UUID id) {
        UserEntity userEntity = userService.getOneById(id);
        return mapper.toTransfer(userEntity);
    }

    @Transactional
    @GetMapping("/")
    public List <UserTransfer> getAllUsers() {
        List <UserEntity> userEntities = userService.getAll();
        return userEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/{id}/friends")
    public List <UserTransfer> getUserFriends(@PathVariable UUID id) {
        List <UserEntity> userEntities = userService.getUserFriends(id);
        return userEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/")
    public UserTransfer addUser(@RequestBody @Valid UserTransfer userTransfer) {
        UserEntity userEntity = mapper.toEntity(userTransfer);
        return mapper.toTransfer(userService.add(userEntity));
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteComments(id);
        userService.deleteEvents(id);
        userService.deleteTemplates(id);
        userService.deleteRelationships(id);
        userService.delete(id);
    }

    @DeleteMapping("/{id}/events")
    public void deleteEvents(@PathVariable UUID id) {
        userService.deleteEvents(id);
    }

    @DeleteMapping("/{id}/templates")
    public void deleteTemplates(@PathVariable UUID id) {
        userService.deleteTemplates(id);
    }

    @DeleteMapping("/{id}/relationships")
    public void deleteRelationships(@PathVariable UUID id) {
        userService.deleteRelationships(id);
    }

    @Transactional
    @PutMapping("/")
    public UserTransfer editUser(@RequestBody UserTransfer userTransfer) {
        UserEntity userEntity = mapper.toEntity(userTransfer);
        return mapper.toTransfer(userService.update(userEntity));
    }
}
