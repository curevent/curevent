package com.curevent.controllers;

import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.services.RelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/relationships")
public class RelationshipController {
    private final RelationshipService relationshipService;

    @GetMapping("/{id}")
    public RelationshipTransfer getRelationship(@PathVariable UUID id) {
        return relationshipService.getOneById(id);
    }

    @PostMapping("/")
    public RelationshipTransfer addRelationship(@RequestBody @Valid RelationshipTransfer relationshipTransfer) {
        return relationshipService.add(relationshipTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteRelationship(@PathVariable UUID id) {
        relationshipService.delete(id);
    }

    @PutMapping("/")
    public RelationshipTransfer editRelationship(@RequestBody RelationshipTransfer relationshipTransfer) {
        return relationshipService.update(relationshipTransfer);
    }
}
