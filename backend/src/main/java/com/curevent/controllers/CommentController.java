package com.curevent.controllers;

import com.curevent.models.entities.Comment;
import com.curevent.models.transfers.CommentTransfer;
import com.curevent.services.CommentService;
import com.curevent.utils.mapping.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper mapper;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public CommentTransfer getComment(@PathVariable UUID id) {
        Comment comment = commentService.getOneById(id);
        return mapper.toTransfer(comment);
    }

    @PostMapping("/")
    public CommentTransfer addComment(@RequestBody @Valid CommentTransfer commentTransfer) {
        Comment comment = mapper.toEntity(commentTransfer);
        return mapper.toTransfer(commentService.add(comment));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.delete(id);
    }

    @PutMapping("/")
    public CommentTransfer editComment(@RequestBody CommentTransfer commentTransfer) {
        Comment comment = mapper.toEntity(commentTransfer);
        return mapper.toTransfer(commentService.update(comment));
    }
}
