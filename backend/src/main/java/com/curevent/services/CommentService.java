package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Comment;
import com.curevent.models.transfers.CommentTransfer;
import com.curevent.repositories.CommentRepository;
import com.curevent.utils.mapping.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final CommentMapper mapper;

    private Comment getEntityById(UUID id) {
        return commentRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Comment" + id));
    }

    public CommentTransfer getOneById(UUID id) {
        Comment comment = commentRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Comment"+id));
        return mapper.toTransfer(comment);
    }

    public CommentTransfer add(CommentTransfer commentTransfer) {
        Comment comment = mapper.toEntity(commentTransfer);
        return mapper.toTransfer(commentRepository.save(comment));
    }

    public void delete(UUID id) {
        Comment comment = getEntityById(id);
        commentRepository.delete(comment);
    }

    public CommentTransfer update(CommentTransfer commentTransfer) {
        Comment comment = mapper.toEntity(commentTransfer);
        if (!commentRepository.existsById(comment.getId())) {
            throw new NotFoundException("No such Comment" + comment.getId());
        }
        return mapper.toTransfer(commentRepository.save(comment));
    }
}
