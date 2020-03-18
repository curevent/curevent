package com.curevent.controllers;

import com.curevent.exceptions.TagNotFoundException;
import com.curevent.models.entities.Tag;
import com.curevent.models.transfers.TagTransfer;
import com.curevent.services.TagService;
import com.curevent.utils.mapping.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
@Transactional
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
        Tag tag = tagService.getOneById(id);
        if (tag == null) {
            throw new TagNotFoundException(id);
        }
        return mapper.toTransfer(tag);
    }

    @PostMapping("/")
    public TagTransfer addTag(@RequestBody @Valid TagTransfer tagTransfer) {
        Tag tag = mapper.toEntity(tagTransfer);
        return mapper.toTransfer(tagService.add(tag));
    }
}
