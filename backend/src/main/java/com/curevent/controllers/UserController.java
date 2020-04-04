package com.curevent.controllers;

import com.curevent.models.entities.Event;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.TimelineService;
import com.curevent.services.UserService;
import com.curevent.utils.mapping.EventMapper;
import com.curevent.utils.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TimelineService timelineService;

    @Autowired
    public UserController(UserService userService, TimelineService timelineService, UserMapper userMapper, EventMapper eventMapper) {
        this.userService = userService;
        this.timelineService = timelineService;
    }

    @GetMapping("/{id}")
    public UserTransfer getUser(@PathVariable UUID id) {
        return userService.getOneById(id);
    }

    @GetMapping("/")
    public List <UserTransfer> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}/friends")
    public List <UserTransfer> getUserFriends(@PathVariable UUID id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/events")
    public List<EventTransfer> getUserEventsInInterval(@PathVariable UUID id,
                                                       @RequestParam(value = "interval", defaultValue = "720") Long interval) {
        return timelineService.getEventsInInterval(id, interval);
    }

    @GetMapping("/{id}/friends/events")
    public List<EventTransfer> getUserFriendsEventsInInterval(@PathVariable UUID id,
                                                              @RequestParam(value = "interval",  defaultValue = "720") Long interval) {
        return timelineService.getFriendsEventsInInterval(id, interval);
    }

    @PostMapping("/")
    public UserTransfer addUser(@RequestBody UserTransfer userTransfer) {
        return userService.add(userTransfer);
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
    public UserTransfer deleteEvents(@PathVariable UUID id) {
        userService.deleteEvents(id);
        return userService.getOneById(id);
    }

    @DeleteMapping("/{id}/templates")
    public UserTransfer deleteTemplates(@PathVariable UUID id) {
        userService.deleteTemplates(id);
        return userService.getOneById(id);
    }

    @DeleteMapping("/{id}/friends")
    public UserTransfer deleteRelationships(@PathVariable UUID id) {
        userService.deleteRelationships(id);
        return userService.getOneById(id);
    }

    @PutMapping("/")
    public UserTransfer editUser(@RequestBody UserTransfer userTransfer) {
        return userService.update(userTransfer);
    }
}
