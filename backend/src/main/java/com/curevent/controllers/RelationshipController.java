package com.curevent.controllers;

import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.RelationshipService;
import com.curevent.services.UserService;
import com.curevent.utils.mapping.RelationshipMapper;
import com.curevent.utils.mapping.UserMapper;
import com.curevent.utils.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public List<RelationshipTransfer> getByOwnerId(@PathVariable UUID id) {
        List<RelationshipEntity> relationshipEntities = relationshipService.getByOwnerId(id);
        return relationshipEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addUser(@RequestBody @Valid RelationshipTransfer relationshipTransfer) {
        RelationshipEntity relationshipEntity = mapper.toEntity(relationshipTransfer);
        relationshipService.add(relationshipEntity);
    }
}
