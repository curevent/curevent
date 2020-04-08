package com.curevent.controllers;

import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.TimelineService;
import com.curevent.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final TimelineService timelineService;

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
