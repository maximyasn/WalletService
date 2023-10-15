package com.maximyasn.data;

import com.maximyasn.core.entities.Transaction;

import java.util.HashMap;
import java.util.UUID;

/**
 * Класс, представляющий собой хранилище транзакций,
 * совершенных в текущей сессии. Данные организованы в виде
 * HashMap. Данные хранятся только во время работы приложения,
 * очищаясь при его повторном запуске.
 */
public class Transactions {


    /**
     * Структура данных, представляющая хранилище транзакций.
     */
    private static HashMap<UUID, Transaction> transactions = new HashMap<>();

    private Transactions() {
    }

    public static HashMap<UUID, Transaction> getTransactions() {
        return transactions;
    }

}
