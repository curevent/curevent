package com.curevent.exceptions;

public class CategoryNotFoundException extends IllegalArgumentException {
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
