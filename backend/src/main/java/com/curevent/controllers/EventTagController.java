package com.curevent.controllers;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.transfers.EventTagTransfer;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.services.EventTagService;
import com.curevent.services.RelationshipService;
import com.curevent.utils.mapping.EventTagMapper;
import com.curevent.utils.mapping.RelationshipMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event_tags")
public class EventTagController {

    private final EventTagService eventTagService;
    private final EventTagMapper mapper;

    public EventTagController(EventTagService eventTagService, EventTagMapper mapper) {
        this.eventTagService = eventTagService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public List<EventTagTransfer> getEventTagsByEventId(@PathVariable UUID id) {
        List<EventTagEntity> eventTagEntities = eventTagService.getByEventId(id);
        return eventTagEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addEventTag(@RequestBody @Valid EventTagTransfer eventTagTransfer) {
        EventTagEntity eventTagEntity = mapper.toEntity(eventTagTransfer);
        eventTagService.add(eventTagEntity);
    }
}
