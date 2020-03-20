package com.curevent.controllers;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.EventService;
import com.curevent.utils.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@Transactional
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
        Event event = eventService.getOneById(id);
        return mapper.toTransfer(event);
    }

    @GetMapping("/all/{id}")
    public List<EventTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<Event> eventEntities = eventService.getAllByOwnerId(id);
        return eventEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/")
    public EventTransfer addEvent(@RequestBody @Valid EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        return mapper.toTransfer(eventService.add(event));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable UUID id) {
        eventService.delete(id);
    }

    @PutMapping("/")
    public EventTransfer editTemplate(@RequestBody EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        return mapper.toTransfer(eventService.update(event));
    }

}
