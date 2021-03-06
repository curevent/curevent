package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Relationship;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.repositories.RelationshipRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
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
        Optional<Relationship> equalsRelationship= relationshipRepository.findEqualsRelationship(relationship.getOwnerId(),
                relationship.getFriendId(),
                relationship.getCategory().getId());
        if(equalsRelationship.isPresent()) {
            throw new ConflictException("Relationship with ownerId " + relationship.getOwnerId() +
                    ", friendId " + relationship.getFriendId() +
                    ", categoryId " + relationship.getCategory().getId() + " already exists");
        }
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
        Optional<Relationship> equalsRelationship= relationshipRepository.findEqualsRelationship(relationship.getOwnerId(),
                relationship.getFriendId(),
                relationship.getCategory().getId());
        if(equalsRelationship.isPresent() && !equalsRelationship.get().getId().equals(relationship.getId())) {
            throw new ConflictException("Relationship with ownerId " + relationship.getOwnerId() +
                    ", friendId " + relationship.getFriendId() +
                    ", categoryId " + relationship.getCategory().getId() + " already exists");
        }
        return mapper.map(relationshipRepository.save(relationship), RelationshipTransfer.class);
    }
}
