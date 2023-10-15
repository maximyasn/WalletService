package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка, связанная с недостаточным балансом пользователя
 */

public class NegativeBalanceException extends Exception {

    public NegativeBalanceException() {
    }

    public NegativeBalanceException(String message) {
        super(message);
    }
}
