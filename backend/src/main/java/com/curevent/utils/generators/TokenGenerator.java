package com.curevent.utils.generators;

import java.util.UUID;

public class TokenGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generate(String key) {
        if (key != null) {
            return UUID.nameUUIDFromBytes(key.getBytes()).toString().replaceAll("-", "");
        } else {
            return TokenGenerator.generate();
        }
    }
}
