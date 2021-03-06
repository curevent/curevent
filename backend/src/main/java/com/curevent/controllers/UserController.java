package com.curevent.controllers;

import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.FilterService;
import com.curevent.services.TimelineService;
import com.curevent.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TimelineService timelineService;
    private final FilterService filterService;

    @GetMapping("/{id}")
    public UserTransfer getUser(@PathVariable UUID id) {
        return userService.getOneById(id);
    }

    @GetMapping("/search")
    public List<UserTransfer> findUser(@RequestParam(value = "username", required = false) String username,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "surname", required = false) String surname) {
        return userService.findUser(username, name, surname);
    }

    @GetMapping("/")
    public List <UserTransfer> getAllUsers(@RequestParam(value = "limit", defaultValue = "20") Long limit,
                                            @RequestParam(value = "offset", defaultValue = "0") Long offset) {
        return userService.getAll(limit, offset);
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

    @GetMapping("/{id}/events/search")
    public List<EventTransfer> filterEventsByTags(@PathVariable UUID id,
                                                     @RequestParam(value = "tagsId") List<UUID> tagsId,
                                                     @RequestParam(value = "interval",  required = false) Long interval) {
        return filterService.filterEventsByTags(id, tagsId, interval);
    }

    @GetMapping("/{id}/friends/events")
    public List<EventTransfer> getUserFriendsEventsInInterval(@PathVariable UUID id,
                                                              @RequestParam(value = "interval",  defaultValue = "720") Long interval) {
        return timelineService.getFriendsEventsInInterval(id, interval);
    }

    @GetMapping("/{id}/friends/events/search")
    public List<EventTransfer> filterFriendsEventsByTags(@PathVariable UUID id,
                                                  @RequestParam(value = "tagsId") List<UUID> tagsId,
                                                  @RequestParam(value = "interval") Long interval) {
        return filterService.filterFriendsEventsByTags(id, tagsId, interval);
    }

    @GetMapping("/{id}/templates/search")
    public List<TemplateTransfer> filterTemplatesByTags(@PathVariable UUID id,
                                                        @RequestParam(value = "tagsId") List<UUID> tagsId) {
        return filterService.filterTemplatesByTags(id, tagsId);
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
