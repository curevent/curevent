package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Relationship;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.repositories.RelationshipRepository;
import com.curevent.utils.mapping.RelationshipMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class RelationshipService {

    @Autowired
    private final RelationshipRepository relationshipRepository;
    @Autowired
    private final RelationshipMapper mapper;

    private Relationship getEntityById(UUID id) {
        return relationshipRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Relationship " + id));
    }

    public RelationshipTransfer getOneById(UUID id) {
        Relationship relationship = relationshipRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Relationship"+id));
        return mapper.toTransfer(relationship);
    }

    public RelationshipTransfer add(RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.toEntity(relationshipTransfer);
        return mapper.toTransfer(relationshipRepository.save(relationship));
    }

    public void delete(UUID id) {
        Relationship relationship = getEntityById(id);
        relationshipRepository.delete(relationship);
    }

    public RelationshipTransfer update(RelationshipTransfer relationshipTransfer) {
        Relationship relationship = mapper.toEntity(relationshipTransfer);
        if (!relationshipRepository.existsById(relationship.getId())) {
            throw new NotFoundException("No such Relationship" + relationship.getId());
        }
        return mapper.toTransfer(relationshipRepository.save(relationship));
    }
}
