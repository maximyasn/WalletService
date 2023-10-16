package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка валидации пароля пользователя
 */

public class PasswordValidationException extends Exception {
    public PasswordValidationException() {
    }

    public PasswordValidationException(String message) {
        super(message);
    }
}
