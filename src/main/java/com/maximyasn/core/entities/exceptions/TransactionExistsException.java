package com.maximyasn.core.entities.exceptions;

/**
 * Ошибка, возникающая при попытке создания транзакции с
 * неуникальным ID
 */

public class TransactionExistsException extends Exception {

    public TransactionExistsException() {
    }

    public TransactionExistsException(String message) {
        super(message);
    }
}
