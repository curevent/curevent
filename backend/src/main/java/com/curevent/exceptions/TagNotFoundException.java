package com.curevent.exceptions;

import java.util.UUID;

public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(UUID id) {
        super("Tag with id " + id + " not found");
    }
}
