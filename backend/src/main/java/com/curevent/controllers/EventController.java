package com.curevent.controllers;

import com.curevent.models.transfers.EventTransfer;
import com.curevent.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventTransfer getEvent(@PathVariable UUID id) {
        return eventService.getOneById(id);
    }

    @PostMapping("/")
    public EventTransfer addEvent(@RequestBody @Valid EventTransfer eventTransfer) {
        return eventService.add(eventTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable UUID id) {
        eventService.delete(id);
    }

    @PutMapping("/")
    public EventTransfer editEvent(@RequestBody EventTransfer eventTransfer) {
        return eventService.update(eventTransfer);
    }
}
