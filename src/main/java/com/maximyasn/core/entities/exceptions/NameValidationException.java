package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка валидации имени пользователя
 */

public class NameValidationException extends Exception {
    public NameValidationException() {
    }

    public NameValidationException(String message) {
        super(message);
    }
}
