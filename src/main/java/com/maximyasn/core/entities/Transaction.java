package com.maximyasn.core.entities;

import com.maximyasn.core.entities.enums.TransactionType;



/** Класс, представляющий сущность транзакции */

public class Transaction {

    /** Идентификатор транзакции */
    private Integer id;

    /** Игрок-создатель транзакции */
    private Player player;

    /** Тип транзакции из перечисления TransactionType */
    private TransactionType transactionType;

    /** Сумма, участвующая в транзакции */
    private Double moneyCount;

    /**
     * Конструктор класса Транзакции
     * @param player игрок-создатель транзакции
     * @param transactionType тип транзакции
     * @param moneyCount сумма транзакции
     */
    public Transaction(Player player, TransactionType transactionType, Double moneyCount) {
        this.player = player;
        this.transactionType = transactionType;
        this.moneyCount = moneyCount;
    }

    public Transaction(TransactionType transactionType, Double moneyCount) {
        this.transactionType = transactionType;
        this.moneyCount = moneyCount;
    }

    public Double getMoneyCount() {
        return moneyCount;
    }

    public Integer getId() {
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
        return "Transaction {" +
               "transactionType=" + transactionType +
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
