package com.maximyasn.core.repo;

import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.exceptions.TransactionExistsException;
import com.maximyasn.data.Transactions;

import java.util.HashMap;
import java.util.UUID;

public class TransactionRepo {

    /**
     * Метод для добавления транзакции в хранилище транзакций
     * текущей сессии.
     *
     * @param transaction транзакция для добавления
     * @throws TransactionExistsException ошибка, возникающая
     *                                    при совпадении ID добавляемой транзакции с ID уже
     *                                    имеющейся транзакции.
     */
    public static void addTransaction(Transaction transaction) throws TransactionExistsException {
        if (transaction != null) {
            if (Transactions.getTransactions().containsKey(transaction.getId())) {
                throw new TransactionExistsException("ID транзакции не уникален!");
            }
            Transactions.getTransactions().put(transaction.getId(), transaction);
        }
    }

    public static HashMap<UUID, Transaction> getTransactions() {
        return Transactions.getTransactions();
    }
}
