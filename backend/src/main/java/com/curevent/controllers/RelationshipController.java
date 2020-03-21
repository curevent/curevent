package com.curevent.controllers;

import com.curevent.models.entities.Comment;
import com.curevent.models.entities.Relationship;
import com.curevent.models.transfers.CommentTransfer;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.services.RelationshipService;
import com.curevent.utils.mapping.RelationshipMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {

    private final RelationshipService relationshipService;
    private final RelationshipMapper mapper;

    public RelationshipController(RelationshipService relationshipService, RelationshipMapper mapper) {
        this.relationshipService = relationshipService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public RelationshipTransfer getComment(@PathVariable UUID id) {
        Relationship relationship = relationshipService.getOneById(id);
        return mapper.toTransfer(relationship);
    }

    @PostMapping("/")
    public RelationshipTransfer addRelationship(@RequestBody @Valid RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.toEntity(relationshipTransfer);
        return mapper.toTransfer(relationshipService.add(relationship));
    }

    @DeleteMapping("/{id}")
    public void deleteRelationship(@PathVariable UUID id) {
        relationshipService.delete(id);
    }

    @PutMapping("/")
    public RelationshipTransfer editRelationship(@RequestBody RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.toEntity(relationshipTransfer);
        return mapper.toTransfer(relationshipService.update(relationship));
    }
}
