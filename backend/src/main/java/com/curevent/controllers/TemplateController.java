package com.curevent.controllers;

import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.TemplateService;
import com.curevent.utils.mapping.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
@Transactional
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper mapper;

    @Autowired
    public TemplateController(TemplateService templateService, TemplateMapper mapper) {
        this.templateService = templateService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public TemplateTransfer getTemplate(@PathVariable UUID id) {
        Template template = templateService.getOneById(id);
        if (template == null) {
            throw new TemplateNotFoundException(id);
        }
        return mapper.toTransfer(template);
    }

    @GetMapping("/all/{id}")
    public List<TemplateTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<Template> templateEntities = templateService.getAllByOwnerId(id);
        return templateEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/")
    public TemplateTransfer addTemplate(@RequestBody @Valid TemplateTransfer templateTransfer) {
        Template template = mapper.toEntity(templateTransfer);
        templateService.add(template);
        return templateTransfer;
    }
}
