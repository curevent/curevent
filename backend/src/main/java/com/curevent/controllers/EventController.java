package com.curevent.controllers;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.models.entities.EventEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.services.EventService;
import com.curevent.utils.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public EventTransfer getEvent(@PathVariable UUID id) {
        EventEntity eventEntity = eventService.getOneById(id);
        if (eventEntity == null) {
            throw new EventNotFoundException();
        }
        return mapper.toTransfer(eventEntity);
    }

    @PostMapping("/add")
    public void addEvent(@RequestBody @Valid EventTransfer eventTransfer) {
        EventEntity eventEntity = mapper.toEntity(eventTransfer);
        eventService.add(eventEntity);
    }
}
