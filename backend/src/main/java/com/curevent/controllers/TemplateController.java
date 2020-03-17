package com.curevent.controllers;

import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.entities.TemplateEntity;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.TemplateService;
import com.curevent.utils.mapping.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
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
        TemplateEntity templateEntity = templateService.getOneById(id);
        if (templateEntity == null) {
            throw new TemplateNotFoundException();
        }
        return mapper.toTransfer(templateEntity);
    }

    @GetMapping("all/{id}")
    public List<TemplateTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<TemplateEntity> templateEntities = templateService.getAllByOwnerId(id);
        return templateEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public TemplateTransfer addTemplate(@RequestBody @Valid TemplateTransfer templateTransfer) {
        TemplateEntity templateEntity = mapper.toEntity(templateTransfer);
        templateService.add(templateEntity);
        return templateTransfer;
    }
}
