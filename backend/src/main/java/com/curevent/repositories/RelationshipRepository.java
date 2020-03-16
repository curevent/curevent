package com.curevent.repositories;

import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipEntity, UUID> {
    List<RelationshipEntity> findByOwnerId(UUID owner_id);
}
