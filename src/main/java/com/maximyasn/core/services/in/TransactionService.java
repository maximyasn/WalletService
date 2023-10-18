package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.core.entities.exceptions.NegativeBalanceException;
import com.maximyasn.core.repo.RepoInterface;
import com.maximyasn.data.Journal;

/**
 * Сервисный класс, предоставляющий метод для
 * обработки транзакций
 */
public class TransactionService implements DefaultService<Transaction>{

    private final RepoInterface<Transaction> repository;

    public TransactionService(RepoInterface<Transaction> repository) {
        this.repository = repository;
    }

    /**
     * Метод для осуществления транзакции
     * @param player игрок, от чьего имени осуществляется транзакция
     * @param transaction - экземпляр транзакции к осуществлению
     * @throws NegativeBalanceException недостаточно средств на балансе
     */
    public void doTransaction(RepoInterface<Player> playerRepo, Player player, Transaction transaction) throws NegativeBalanceException {
        switch (transaction.getTransactionType()) {
            case DEBIT -> {
                Journal.put("Пользователь " + player.getName() + " снимает средства", EventStatus.SUCCESS);
                if (player.getBalance() - transaction.getMoneyCount() >= 0) {
                    repository.add(transaction);
                    Journal.put("Транзакция " + transaction.getId() + " добавлена в историю транзакций", EventStatus.SUCCESS);
                    playerRepo.update(player.getName(), -transaction.getMoneyCount());
                    Journal.put("Снятие средств успешно завершено", EventStatus.SUCCESS);
                } else {
                    Journal.put("Снятие средств успешно завершено", EventStatus.FAIlED);
                    throw new NegativeBalanceException("Недостаточно средств на балансе!");
                }
            }
            case CREDIT -> {
                Journal.put("Пользователь " + player.getName() + " пополняет баланс", EventStatus.SUCCESS);
                repository.add(transaction);
                Journal.put("Транзакция " + transaction.getId() + " добавлена в историю транзакций", EventStatus.SUCCESS);
                playerRepo.update(player.getName(), transaction.getMoneyCount());
                Journal.put("Пополнение баланса успешно завершено", EventStatus.SUCCESS);
            }
        }
    }
}
