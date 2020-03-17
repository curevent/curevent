package com.curevent.exceptions;

import java.util.UUID;

public class CommentNotFoundException extends IllegalArgumentException {
    public CommentNotFoundException(UUID id) {
        super("Comment with id " + id + " not found");
    }
}
