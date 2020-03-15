package com.curevent.exceptions;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(String argument) {
        super("User with this " + argument + " already exists");
    }
}
