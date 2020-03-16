package com.curevent.exceptions;

public class CommentNotFoundException extends IllegalArgumentException {
    public CommentNotFoundException() {
        super("Comment not found");
    }
}
