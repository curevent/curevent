package com.curevent.controllers;

import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.EventFactory;
import com.curevent.services.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/templates")
public class TemplateController {
    private final TemplateService templateService;
    private final EventFactory eventFactory;

    @GetMapping("/{id}")
    public TemplateTransfer getTemplate(@PathVariable UUID id) {
        return templateService.getOneById(id);
    }

    @PostMapping("/")
    public TemplateTransfer addTemplate(@RequestBody TemplateTransfer templateTransfer) {
        return templateService.add(templateTransfer);
    }

    @PostMapping("/{id}/events")
    public TemplateTransfer createEvents(@PathVariable UUID id, @RequestBody RepeatForm repeatForm) {
        return eventFactory.parseRepeatForm(id, repeatForm);
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable UUID id) {
        templateService.delete(id);
    }

    @DeleteMapping("/{id}/events")
    public TemplateTransfer deleteEvents(@PathVariable UUID id) {
        return templateService.deleteEvents(id);
    }

    @PutMapping("/")
    public TemplateTransfer editTemplate(@RequestBody TemplateTransfer templateTransfer) {
        templateService.update(templateTransfer);
        return templateService.updateEvents(templateTransfer);
    }
}
