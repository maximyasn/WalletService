package com.maximyasn.core.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий собой сущность игрока
 */

public class Player {

    /** Имя игрока */
    private String name;

    /** Пароль игрока */
    private String password;

    /** Баланс игрока */
    private BigDecimal balance;

    /** История транзакций игрока */
    private List<Transaction> transactionHistory;

    /**
     * Конструктор класса Игрок
     * @param name имя
     * @param password пароль
     * Балансу присваивается значение 0
     * Истории транзакций присваивается пустой ArrayList
     */
    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = BigDecimal.ZERO;
        transactionHistory = new ArrayList<>();
    }

    /**
     * Генератор случайного ID, предоставляемый игроком
     * @return уникальный идентификатор UUID
     */
    public UUID getTransactionID() {
        return UUID.randomUUID();
    }

    public List<Transaction> getTransactionsHistory() {
        return transactionHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Player{" +
               "name='" + name + '\'' +
               ", balance=" + balance +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;
        return password.equals(player.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
