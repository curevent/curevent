package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Comment;
import com.curevent.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return commentRepository.findById(id).stream().findAny()
                .orElseThrow(()-> new NotFoundException("No such Comment"+ id));
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(UUID id) {
        Comment comment = getOneById(id);
        commentRepository.delete(comment);
    }

    public Comment update(Comment comment) {
        if (!commentRepository.existsById(comment.getId())) {
            throw new NotFoundException("No such Comment" + comment.getId());
        }
        return commentRepository.save(comment);
    }
}
