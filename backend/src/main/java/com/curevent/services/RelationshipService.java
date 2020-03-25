package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Relationship;
import com.curevent.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipService(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }


    public Relationship getOneById(UUID id) {
        return relationshipRepository.findById(id).stream().findAny()
                .orElseThrow(()-> new NotFoundException("No such Relationship" + id));
    }

    public Relationship add(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    public void delete(UUID id) {
        Relationship relationship = getOneById(id);
        relationshipRepository.delete(relationship);
    }

    public Relationship update(Relationship relationship) {
        if (!relationshipRepository.existsById(relationship.getId())) {
            throw new NotFoundException("No such Relationship" + relationship.getId());
        }
        return relationshipRepository.save(relationship);
    }
}
