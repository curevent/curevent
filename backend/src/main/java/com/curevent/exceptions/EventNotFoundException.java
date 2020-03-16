package com.curevent.exceptions;

public class EventNotFoundException extends IllegalArgumentException {
    public EventNotFoundException() {
        super("Event not found");
    }
}
