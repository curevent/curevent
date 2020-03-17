package com.curevent.controllers;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.TemplateEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.EventService;
import com.curevent.utils.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("all/{id}")
    public List<EventTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<EventEntity> eventEntities = eventService.getAllByOwnerId(id);
        return eventEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public EventTransfer addEvent(@RequestBody @Valid EventTransfer eventTransfer) {
        EventEntity eventEntity = mapper.toEntity(eventTransfer);
        eventService.add(eventEntity);
        return eventTransfer;
    }
}
