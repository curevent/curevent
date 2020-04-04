package com.curevent.controllers;

import com.curevent.models.transfers.TagTransfer;
import com.curevent.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private final TagService tagService;

    @GetMapping("/{id}")
    public TagTransfer getTag(@PathVariable UUID id) {
        return tagService.getOneById(id);
    }

    @PostMapping("/")
    public TagTransfer addTag(@RequestBody @Valid TagTransfer tagTransfer) {
        return tagService.add(tagTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable UUID id) {
        tagService.delete(id);
    }

    @PutMapping("/")
    public TagTransfer editTag(@RequestBody TagTransfer tagTransfer) {
        return tagService.update(tagTransfer);
    }
}
