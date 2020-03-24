package com.curevent.controllers;

import com.curevent.models.entities.Event;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.services.EventService;
import com.curevent.utils.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper mapper;

    @Autowired
    public EventController(EventService eventService, EventMapper mapper) {
        this.eventService = eventService;
        this.mapper = mapper;
    }

    @Transactional
    @GetMapping("/{id}")
    public EventTransfer getEvent(@PathVariable UUID id) {
        Event event = eventService.getOneById(id);
        return mapper.toTransfer(event);
    }

    @Transactional
    @PostMapping("/")
    public EventTransfer addEvent(@RequestBody @Valid EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        return mapper.toTransfer(eventService.add(event));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable UUID id) {
        eventService.deleteComments(id);
        eventService.delete(id);
    }

    @Transactional
    @PutMapping("/")
    public EventTransfer editEvent(@RequestBody EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        return mapper.toTransfer(eventService.update(event));
    }

}
