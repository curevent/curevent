package com.curevent.models.enums;

public enum DefaultCategory {
    PRIVATE(0L),
    ALL_FRIENDS(1L);

    private final long id;

    DefaultCategory(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }
}
