package com.api.api.exception;

public class CategoryExists extends RuntimeException {
    public CategoryExists(String message) {
        super(message);
    }
}
