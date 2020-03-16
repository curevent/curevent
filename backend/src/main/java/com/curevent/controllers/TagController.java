package com.curevent.controllers;

import com.curevent.exceptions.TagNotFoundException;
import com.curevent.models.entities.TagEntity;
import com.curevent.models.transfers.TagTransfer;
import com.curevent.services.TagService;
import com.curevent.utils.mapping.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper mapper;

    @Autowired
    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public TagTransfer getTag(@PathVariable UUID id) {
        TagEntity tagEntity = tagService.getOneById(id);
        if (tagEntity == null) {
            throw new TagNotFoundException();
        }
        return mapper.toTransfer(tagEntity);
    }

    @PostMapping("/add")
    public void addTag(@RequestBody @Valid TagTransfer tagTransfer) {
        TagEntity tagEntity = mapper.toEntity(tagTransfer);
        tagService.add(tagEntity);
    }
}
