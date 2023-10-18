package com.maximyasn.core.entities;



/**
 * Класс, представляющий собой сущность игрока
 */

public class Player {

    private Integer id;

    /** Имя игрока */
    private String name;

    /** Пароль игрока */
    private String password;

    /** Баланс игрока */
    private Double balance;


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
        this.balance = 0.0;
    }

    public Player(Integer id, String name, String password, Double balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    /**
     * Генератор случайного ID, предоставляемый игроком
     * @return уникальный идентификатор UUID
     */

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Double getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
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
