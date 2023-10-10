package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.data.Journal;

import java.util.List;

/**
 * Сервисный класс, предоставляющий операции над
 * экземплярами классов игроков
 */
public class PlayerService {

    /**
     * Метод, выводящий в консоль текущий бланас игрока
     * @param player игрок, с чьего аккаунта ведутся действия
     */
    public void checkCurrentBalance(Player player) {
        if (player != null) {
            System.out.println(player.getBalance());
            System.out.println("\n");
            Journal.put("Пользователь " + player.getName() + " просмотрел свой баланс", EventStatus.SUCCESS);
        } else {
            Journal.put("Пользователь ... просмотрел свой баланс", EventStatus.FAIlED);
        }
    }

    /**
     * Метод, выводящий в консоль текущую историю транзакций игрока
     * @param player игрок, с чьего аккаунта ведутся действия
     */
    public void getPlayersTransactionHistory(Player player) {
        if(player != null) {
            List<Transaction> transactions = player.getTransactionsHistory();
            if(transactions.isEmpty()) {
                System.out.println("История транзакций пуста.\n");
            } else {
                transactions.forEach(System.out::println);
            }
            System.out.println("\n\n");
            Journal.put("Пользователь " + player.getName() + " просмотрел историю своих транзакций", EventStatus.SUCCESS);
        } else {
            Journal.put("Пользователь ... просмотрел историю своих транзакций", EventStatus.FAIlED);
            System.out.println("Игрока не существует!");
        }
    }
}
