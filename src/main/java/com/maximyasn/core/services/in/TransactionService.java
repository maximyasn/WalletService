package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.core.entities.exceptions.NegativeBalanceException;
import com.maximyasn.core.entities.exceptions.TransactionExistsException;
import com.maximyasn.core.repo.TransactionRepo;
import com.maximyasn.data.Journal;

/**
 * Сервисный класс, предоставляющий метод для
 * обработки транзакций
 */
public class TransactionService {

    /**
     * Метод для осуществления транзакции
     * @param player игрок, от чьего имени осуществляется транзакция
     * @param transaction - экземпляр транзакции к осуществлению
     * @throws NegativeBalanceException недостаточно средств на балансе
     * @throws TransactionExistsException транзакция с данным ID уже существует
     */
    public static void doTransaction(Player player, Transaction transaction) throws NegativeBalanceException, TransactionExistsException {
        switch (transaction.getTransactionType()) {
            case DEBIT -> {
                Journal.put("Пользователь " + player.getName() + " снимает средства", EventStatus.SUCCESS);
                if (player.getBalance().subtract(transaction.getMoneyCount()).doubleValue() >= 0) {
                    TransactionRepo.addTransaction(transaction);
                    Journal.put("Транзакция " + transaction.getId() + " добавлена в историю транзакций", EventStatus.SUCCESS);
                    player.setBalance(player.getBalance().subtract(transaction.getMoneyCount()));
                    player.getTransactionsHistory().add(transaction);
                    Journal.put("Снятие средств успешно завершено", EventStatus.SUCCESS);
                } else {
                    Journal.put("Снятие средств успешно завершено", EventStatus.FAIlED);
                    throw new NegativeBalanceException("Недостаточно средств на балансе!");
                }
            }
            case CREDIT -> {
                Journal.put("Пользователь " + player.getName() + " пополняет баланс", EventStatus.SUCCESS);
                TransactionRepo.addTransaction(transaction);
                Journal.put("Транзакция " + transaction.getId() + " добавлена в историю транзакций", EventStatus.SUCCESS);
                player.setBalance(player.getBalance().add(transaction.getMoneyCount()));
                player.getTransactionsHistory().add(transaction);
                Journal.put("Пополнение баланса успешно завершено", EventStatus.SUCCESS);
            }
        }
    }
}
