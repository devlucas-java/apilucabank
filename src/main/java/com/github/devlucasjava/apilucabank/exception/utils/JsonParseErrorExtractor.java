package com.github.devlucasjava.apilucabank.exception.utils;

import org.springframework.http.converter.HttpMessageNotReadableException;
import tools.jackson.databind.exc.InvalidFormatException;

public class JsonParseErrorExtractor {

    public static String extractMessage(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormat) {

            String fieldName = invalidFormat.getPath()
                    .stream()
                    .map(ref -> ref.getPropertyName())
                    .reduce((first, second) -> second)
                    .orElse("unknown");

            Class<?> targetType = invalidFormat.getTargetType();

            return String.format(
                    "Invalid value for field '%s'. Expected type: %s",
                    fieldName,
                    targetType.getSimpleName()
            );
        }

        return "Malformed JSON request";
    }
}