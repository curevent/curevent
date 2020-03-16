package com.curevent.repositories;

import com.curevent.models.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    List <CommentEntity> findByOwnerId(UUID ownerId);
    List<CommentEntity> findByEventId(UUID eventId);
}
