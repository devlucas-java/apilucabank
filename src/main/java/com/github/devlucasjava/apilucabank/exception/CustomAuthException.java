package com.github.devlucasjava.apilucabank.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthException extends AuthenticationException {
    public CustomAuthException(String message) { super(message); }
}
