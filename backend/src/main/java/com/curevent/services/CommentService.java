package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Comment;
import com.curevent.models.transfers.CommentTransfer;
import com.curevent.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    private Comment getEntityById(UUID id) {
        return commentRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Comment" + id));
    }

    public CommentTransfer getOneById(UUID id) {
        Comment comment = commentRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Comment"+id));
        return mapper.map(comment, CommentTransfer.class);
    }

    public CommentTransfer add(CommentTransfer commentTransfer) {
        Comment comment = mapper.map(commentTransfer, Comment.class);
        return mapper.map(commentRepository.save(comment), CommentTransfer.class);
    }

    public void delete(UUID id) {
        Comment comment = getEntityById(id);
        commentRepository.delete(comment);
    }

    public CommentTransfer update(CommentTransfer commentTransfer) {
        Comment comment = mapper.map(commentTransfer, Comment.class);
        if (!commentRepository.existsById(comment.getId())) {
            throw new NotFoundException("No such Comment" + comment.getId());
        }
        return mapper.map(commentRepository.save(comment), CommentTransfer.class);
    }
}
