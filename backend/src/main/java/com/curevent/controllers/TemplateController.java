package com.curevent.controllers;

import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.TemplateService;
import com.curevent.utils.mapping.EventMapper;
import com.curevent.utils.mapping.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;
    private final EventMapper eventMapper;

    @Autowired
    public TemplateController(TemplateService templateService, TemplateMapper templateMapper, EventMapper eventMapper) {
        this.templateService = templateService;
        this.templateMapper = templateMapper;
        this.eventMapper = eventMapper;
    }

    @Transactional
    @GetMapping("/{id}")
    public TemplateTransfer getTemplate(@PathVariable UUID id) {
        Template template = templateService.getOneById(id);
        return templateMapper.toTransfer(template);
    }

    @Transactional
    @GetMapping("/{id}/events")
    public List <EventTransfer> getEvents(@PathVariable UUID id) {
        List <Event> events = templateService.getEvents(id);
        return events.stream().map(eventMapper::toTransfer).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/")
    public TemplateTransfer addTemplate(@RequestBody TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        return templateMapper.toTransfer(templateService.add(template));
    }

    @Transactional
    @PostMapping("/{id}/events")
    public List <EventTransfer> createEvents(@PathVariable UUID id, @RequestParam (value = "time") Long firstTimeAppearance) {
        return templateService.createEvents(id, new Timestamp(firstTimeAppearance))
                .stream().map(eventMapper::toTransfer).collect(Collectors.toList());
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable UUID id) {
        templateService.deleteEvents(id);
        templateService.delete(id);
    }

    @DeleteMapping("/{id}/events")
    public void deleteEvents(@PathVariable UUID id) {
        templateService.deleteEvents(id);
    }

    @Transactional
    @PutMapping("/")
    public TemplateTransfer editTemplate(@RequestBody TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        templateService.updateEvents(template);
        return templateMapper.toTransfer(templateService.update(template));
    }
}
