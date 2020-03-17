package com.curevent.exceptions;

import java.util.UUID;

public class EventNotFoundException extends IllegalArgumentException {
    public EventNotFoundException(UUID id) {
        super("Event with id " + id + " not found");
    }
}
