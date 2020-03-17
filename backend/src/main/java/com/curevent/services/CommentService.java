package com.curevent.services;

import com.curevent.models.entities.CommentEntity;
import com.curevent.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity getOneById(UUID id) {
        return commentRepository.findById(id).stream().findAny().orElse(null);
    }

    public List <CommentEntity> getAllByOwnerId(UUID ownerId) {
        return commentRepository.findByOwnerId(ownerId);
    }

    public List <CommentEntity> getAllByEventId(UUID eventId) {
        return commentRepository.findByEventId(eventId);
    }

    public void add(CommentEntity commentEntity) {
        commentRepository.save(commentEntity);
    }
}
