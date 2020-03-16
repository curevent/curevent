package com.curevent.exceptions;

public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException() {
        super("Tag not found");
    }
}
