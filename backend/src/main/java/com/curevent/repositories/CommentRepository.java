package com.curevent.repositories;

import com.curevent.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List <Comment> findByOwnerId(UUID ownerId);
    List<Comment> findByEventId(UUID eventId);
}
