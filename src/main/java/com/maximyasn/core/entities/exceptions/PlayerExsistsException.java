package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка, возникающая при попытке создания нового аккаунта
 * с именем, уже имеющимся в базе
 */

public class PlayerExsistsException extends Exception {
    public PlayerExsistsException() {
    }

    public PlayerExsistsException(String message) {
        super(message);
    }
}
