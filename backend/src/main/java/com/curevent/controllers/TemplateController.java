package com.curevent.controllers;

import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.TemplateService;
import com.curevent.utils.mapping.EventMapper;
import com.curevent.utils.mapping.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
@Transactional
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

    @GetMapping("/{id}")
    public TemplateTransfer getTemplate(@PathVariable UUID id) {
        Template template = templateService.getOneById(id);
        if (template == null) {
            throw new TemplateNotFoundException(id);
        }
        return templateMapper.toTransfer(template);
    }

    @GetMapping("/all/{id}")
    public List<TemplateTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<Template> templateEntities = templateService.getAllByOwnerId(id);
        return templateEntities.stream().map(templateMapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/")
    public TemplateTransfer addTemplate(@RequestBody TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        return templateMapper.toTransfer(templateService.add(template));
    }

    @PostMapping("/create")
    public List <EventTransfer> createEvents(@RequestParam (value = "id") UUID id, @RequestParam (value = "time") Long firstTimeAppearance) {
        return templateService.createEvents(id, new Timestamp(firstTimeAppearance))
                .stream().map(eventMapper::toTransfer).collect(Collectors.toList());
    }
}
