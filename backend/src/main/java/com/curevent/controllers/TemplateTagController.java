package com.curevent.controllers;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.TemplateTagEntity;
import com.curevent.models.transfers.EventTagTransfer;
import com.curevent.models.transfers.TemplateTagTransfer;
import com.curevent.services.EventTagService;
import com.curevent.services.TemplateTagService;
import com.curevent.utils.mapping.EventTagMapper;
import com.curevent.utils.mapping.TemplateTagMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template_tags")
public class TemplateTagController {

    private final TemplateTagService templateTagService;
    private final TemplateTagMapper mapper;

    public TemplateTagController(TemplateTagService templateTagService, TemplateTagMapper mapper) {
        this.templateTagService = templateTagService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public List<TemplateTagTransfer> getTemplateTagsByEventId(@PathVariable UUID id) {
        List<TemplateTagEntity> templateTagEntities = templateTagService.getByEventId(id);
        return templateTagEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void getTemplateTag(@RequestBody @Valid TemplateTagTransfer templateTagTransfer) {
        TemplateTagEntity templateTagEntity = mapper.toEntity(templateTagTransfer);
        templateTagService.add(templateTagEntity);
    }
}
