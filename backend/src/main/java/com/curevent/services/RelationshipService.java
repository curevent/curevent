package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Relationship;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.repositories.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final ModelMapper mapper;

    private Relationship getEntityById(UUID id) {
        return relationshipRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Relationship " + id));
    }

    public RelationshipTransfer getOneById(UUID id) {
        Relationship relationship = relationshipRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Relationship"+id));
        return mapper.map(relationship, RelationshipTransfer.class);
    }

    public RelationshipTransfer add(RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.map(relationshipTransfer, Relationship.class);
        return mapper.map(relationshipRepository.save(relationship), RelationshipTransfer.class);
    }

    public void delete(UUID id) {
        Relationship relationship = getEntityById(id);
        relationshipRepository.delete(relationship);
    }

    public RelationshipTransfer update(RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.map(relationshipTransfer, Relationship.class);
        if (!relationshipRepository.existsById(relationship.getId())) {
            throw new NotFoundException("No such Relationship" + relationship.getId());
        }
        return mapper.map(relationshipRepository.save(relationship), RelationshipTransfer.class);
    }
}
