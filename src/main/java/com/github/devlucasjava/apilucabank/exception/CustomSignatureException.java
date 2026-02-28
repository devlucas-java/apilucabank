package com.github.devlucasjava.apilucabank.exception;

import io.jsonwebtoken.security.SignatureException;

public class CustomSignatureException extends RuntimeException {
    public CustomSignatureException(String message) {
        super(message);
    }
}
