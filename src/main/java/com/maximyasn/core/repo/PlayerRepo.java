package com.maximyasn.core.repo;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.enums.TransactionType;


import java.sql.*;
import java.util.*;

public class PlayerRepo extends AbstractRepo<Player> {

    public PlayerRepo(Connection connection) {
        super(connection);
    }

    /**
     * Метод, возвращающий список игроков текущей сессии.
     */
    @Override
    public List<Player> getAll() {
        List<Player> res = null;
        try {
            res = new ArrayList<>();
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_ALL_PLAYERS);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                res.add(set.getObject(1, Player.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public Player getById(int id) {
        Player res = null;
        try {
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_PLAYER_BY_ID);
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                res = set.getObject(1, Player.class);
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении игрока по id");
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
        return res;
    }

    /**
     * Добавить игрока в базу
     * @param obj игрок
     */
    @Override
    public void add(Player obj) {
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.ADD_PLAYER);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPassword());
            ps.setDouble(3, obj.getBalance());
            ps.executeUpdate();
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
    }

    /**
     * Получить количество игроков в базе
     * @return количество игроков в базе
     */
    @Override
    public int getAllCount() {
        int res = 0;
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_PLAYERS_COUNT);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                res = set.getInt(1);
            }
            getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении количества игроков");
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
        return res;
    }

    /**
     * Поиск игрока в базе по имени
     * @param name имя икомого игрока
     * @return искомый игрок
     */
    @Override
    public Player getByName(String name) {
        Player res = null;
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_PLAYER_BY_NAME);
            ps.setString(1, name);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                res = new Player(set.getInt("id"),
                        set.getString("name"),
                        set.getString("password"),
                        set.getDouble("balance"));
            }
            getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении игрока по имени");
            e.printStackTrace(System.out);
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
        return res;
    }


    /**
     * Обновить данные о балансе игрока в базе
     * @param name имя игрока
     * @param sum сумма, которая добавится/отнимется от текущего баланса
     */
    @Override
    public void update(String name, Double sum) {
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.UPDATE_PLAYERS_BALANCE_AFTER_TRANSACTION);
            ps.setDouble(1, sum);
            ps.setString(2, name);
            ps.executeUpdate();
            getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при обновлении игрока");
            e.printStackTrace(System.out);
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
    }

    /**
     * Поиск игрока по имени и паролю для метода аутентификации
     * @param name имя игрока
     * @param pass пароль игрока
     * @return искомый игрок
     */
    @Override
    public Player getByNameAndPass(String name, String pass) {
        Player res = null;
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_BY_NAME_AND_PASS);
            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                res = new Player(set.getInt("id"),
                        set.getString("name"),
                        set.getString("password"),
                        set.getDouble("balance"));
            }
            getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении игрока по имени");
            e.printStackTrace(System.out);
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getCause().toString());
            }
        }
        return res;
    }

    /**
     * Получить историю транзакций игрока
     * @param id id игрока
     * @return список транзакций игрока
     */
    @Override
    public List<Transaction> getTransactionHistory(Integer id) {
        List<Transaction> res = new ArrayList<>();
        try {
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_PLAYERS_TRANSACTIONS);
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                res.add(new Transaction(TransactionType.valueOf(set.getString("transaction_type")),
                        set.getDouble("money_count")));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении истории транзакций");
        }
        return res;
    }

    /**
     * Удалить игрока из базы
     * @param id id игрока
     */
    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(Requests.DELETE_PLAYER_BY_ID);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении игрока");
        }
    }
}
