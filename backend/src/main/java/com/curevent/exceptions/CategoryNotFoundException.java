package com.curevent.exceptions;

public class CategoryNotFoundException extends IllegalArgumentException {
    public CategoryNotFoundException(Long id) {
        super("Category with id " + id + " not found");
    }
}
