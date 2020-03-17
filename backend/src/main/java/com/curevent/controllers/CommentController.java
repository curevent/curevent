package com.curevent.controllers;

import com.curevent.exceptions.CommentNotFoundException;
import com.curevent.models.entities.CommentEntity;
import com.curevent.models.entities.EventEntity;
import com.curevent.models.transfers.CommentTransfer;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.services.CommentService;
import com.curevent.utils.mapping.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        CommentEntity commentEntity = commentService.getOneById(id);
        if (commentEntity == null) {
            throw new CommentNotFoundException();
        }
        return mapper.toTransfer(commentEntity);
    }

    @GetMapping("all/{id}")
    public List<CommentTransfer> getRelationshipByOwnerId(@PathVariable UUID id) {
        List<CommentEntity> commentEntities = commentService.getAllByOwnerId(id);
        return commentEntities.stream().map(mapper::toTransfer).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public CommentTransfer addComment(@RequestBody @Valid CommentTransfer commentTransfer) {
        CommentEntity commentEntity = mapper.toEntity(commentTransfer);
        commentService.add(commentEntity);
        return commentTransfer;
    }
}
