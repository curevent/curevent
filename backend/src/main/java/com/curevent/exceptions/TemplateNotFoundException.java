package com.curevent.exceptions;

import java.util.UUID;

public class TemplateNotFoundException extends IllegalArgumentException {
    public TemplateNotFoundException(UUID id ) {
        super("Template with id "+ id + " not found");
    }
}
