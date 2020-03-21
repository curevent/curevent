package com.curevent.exceptions;

import java.util.UUID;

public class RelationshipNotFoundException extends IllegalArgumentException {
    public RelationshipNotFoundException(UUID id) {
        super("Relationship with id " + id + " not found");
    }
}
