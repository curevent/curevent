package com.curevent.controllers;

import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.services.RelationshipService;
import com.curevent.utils.mapping.RelationshipMapper;
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

    @GetMapping("all/{id}")
    public List<RelationshipTransfer> getAllRelationshipByOwnerId(@PathVariable UUID id) {
        List<RelationshipEntity> relationshipEntities = relationshipService.getAllByOwnerId(id);
        return relationshipEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public RelationshipTransfer addRelationship(@RequestBody @Valid RelationshipTransfer relationshipTransfer) {
        RelationshipEntity relationshipEntity = mapper.toEntity(relationshipTransfer);
        relationshipService.add(relationshipEntity);
        return relationshipTransfer;
    }
}
