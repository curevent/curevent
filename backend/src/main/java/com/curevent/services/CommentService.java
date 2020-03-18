package com.curevent.services;

import com.curevent.models.entities.Comment;
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

    public Comment getOneById(UUID id) {
        return commentRepository.findById(id).stream().findAny().orElse(null);
    }

    public List <Comment> getAllByOwnerId(UUID ownerId) {
        return commentRepository.findByOwnerId(ownerId);
    }

    public List <Comment> getAllByEventId(UUID eventId) {
        return commentRepository.findByEventId(eventId);
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }
}
