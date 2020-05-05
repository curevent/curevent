package com.curevent.controllers;

import com.curevent.models.transfers.CommentTransfer;
import com.curevent.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentTransfer getComment(@PathVariable UUID id) {
        return commentService.getOneById(id);
    }

    @PostMapping("/")
    public CommentTransfer addComment(@RequestBody @Valid CommentTransfer commentTransfer) {
        return commentService.add(commentTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.delete(id);
    }

    @PutMapping("/")
    public CommentTransfer editComment(@RequestBody CommentTransfer commentTransfer) {
        return commentService.update(commentTransfer);
    }
}
