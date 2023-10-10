package com.maximyasn.data;

import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.exceptions.TransactionExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Класс, представляющий собой хранилище транзакций,
 * совершенных в текущей сессии. Данные организованы в виде
 * HashMap. Данные хранятся только во время работы приложения,
 * очищаясь при его повторном запуске.
 */
public class Transactions {


    /** Структура данных, представляющая хранилище транзакций. */
    private static HashMap<UUID, Transaction> transactions = new HashMap<>();

    private Transactions() {}


    /**
     * Метод для добавления транзакции в хранилище транзакций
     * текущей сессии.
     * @param transaction транзакция для добавления
     * @throws TransactionExistsException ошибка, возникающая
     * при совпадении ID добавляемой транзакции с ID уже
     * имеющейся транзакции.
     */
    public static void addTransaction(Transaction transaction) throws TransactionExistsException {
        if(transaction != null) {
            for(Map.Entry<UUID, Transaction> entry: transactions.entrySet()) {
                if (entry.getKey().equals(transaction.getId())) {
                    throw new TransactionExistsException("ID транзакции не уникален!");
                }
            }
            transactions.put(transaction.getId(), transaction);
        }
    }
}
