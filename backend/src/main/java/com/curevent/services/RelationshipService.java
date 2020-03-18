package com.curevent.services;

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
        return relationshipRepository.findById(id).stream().findAny().orElse(null);
    }

    public List<Relationship> getAllByOwnerId(UUID ownerId) {
        return relationshipRepository.findByOwnerId(ownerId);
    }

    public void add(Relationship relationship) {
        relationshipRepository.save(relationship);
    }
}
