package com.curevent.controllers;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.EventService;
import com.curevent.services.TimelineService;
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
public class EventController {

    private final EventService eventService;
    private final TimelineService timelineService;
    private final EventMapper mapper;

    @Autowired
    public EventController(EventService eventService, TimelineService timelineService, EventMapper mapper) {
        this.eventService = eventService;
        this.timelineService = timelineService;
        this.mapper = mapper;
    }

    @Transactional
    @GetMapping("/{id}")
    public EventTransfer getEvent(@PathVariable UUID id) {
        Event event = eventService.getOneById(id);
        return mapper.toTransfer(event);
    }

    @Transactional
    @GetMapping("/user/{id}")
    public List<EventTransfer> getUserEventsInInterval(@PathVariable UUID id, @RequestParam(value = "interval") Long interval) {
        List <Event> events = timelineService.getEventsInInterval(id, interval);
        return events.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/user/{id}/friends")
    public List<EventTransfer> getUserFriendsEventsInInterval(@PathVariable UUID id, @RequestParam(value = "interval") Long interval) {
        List <Event> events = timelineService.getFriendsEventsInInterval(id, interval);
        return events.stream().map(mapper::toTransfer).collect(Collectors.toList());
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
