package com.curevent.controllers;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.TemplateEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.EventService;
import com.curevent.services.TemplateService;
import com.curevent.utils.mapping.EventMapper;
import com.curevent.utils.mapping.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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

    @PostMapping("/add")
    public void addTemplate(@RequestBody @Valid TemplateTransfer templateTransfer) {
        TemplateEntity templateEntity = mapper.toEntity(templateTransfer);
        templateService.add(templateEntity);
    }
}
