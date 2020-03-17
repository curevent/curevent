package com.curevent.exceptions;

import java.util.UUID;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(UUID id) {
        super("User with id " + id + " not found");
    }
}
