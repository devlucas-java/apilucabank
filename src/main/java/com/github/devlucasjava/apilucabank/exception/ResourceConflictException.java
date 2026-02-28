package com.github.devlucasjava.apilucabank.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String field) {
        super(field + " already exists: " + field);
    }
}