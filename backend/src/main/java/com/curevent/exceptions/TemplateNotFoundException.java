package com.curevent.exceptions;

public class TemplateNotFoundException extends IllegalArgumentException {
    public TemplateNotFoundException() {
        super("Template not found");
    }
}
