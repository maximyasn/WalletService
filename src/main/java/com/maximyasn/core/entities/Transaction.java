package com.maximyasn.core.entities;

import com.maximyasn.core.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

/** Класс, представляющий сущность транзакции */

public class Transaction {

    /** Идентификатор транзакции */
    private final UUID id;

    /** Игрок-создатель транзакции */
    private Player player;

    /** Тип транзакции из перечисление TransactionType */
    private TransactionType transactionType;

    /** Сумма, участвующая в транзакции */
    private BigDecimal moneyCount;

    /**
     * Конструктор класса Транзакции
     * @param player игрок-создатель транзакции
     * @param transactionType тип транзакции
     * @param moneyCount сумма транзакции
     * @param id идентификатор транзакции
     */
    public Transaction(Player player, TransactionType transactionType, BigDecimal moneyCount, UUID id) {
        this.player = player;
        this.transactionType = transactionType;
        this.moneyCount = moneyCount;
        this.id = id;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "id=" + id +
               ", player=" + player +
               ", transactionType=" + transactionType +
               ", moneyCount=" + moneyCount +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!id.equals(that.id)) return false;
        if (!player.equals(that.player)) return false;
        if (transactionType != that.transactionType) return false;
        return moneyCount.equals(that.moneyCount);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + player.hashCode();
        result = 31 * result + transactionType.hashCode();
        result = 31 * result + moneyCount.hashCode();
        return result;
    }
}
