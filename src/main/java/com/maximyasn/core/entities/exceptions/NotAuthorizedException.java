package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка, возникающая при попытке войти в свой аккаунт
 * для неавторизованного пользователя
 */

public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
