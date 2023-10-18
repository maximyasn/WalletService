package com.maximyasn.core.repo;


import com.maximyasn.core.entities.Transaction;


import java.sql.*;
import java.util.*;

public class TransactionRepo extends AbstractRepo<Transaction> {

    public TransactionRepo(Connection connection) {
        super(connection);
    }

    /**
     * Поиск транзакции по id
     *
     * @param id id транзакции
     * @return искомая транзакция
     */
    @Override
    public Transaction getById(int id) {
        Transaction res = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement(Requests.GET_TRANSACTION_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getObject(1, Transaction.class);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Добавить транзакцию в базу
     *
     * @param obj добавляемая транзакция
     */
    @Override
    public void add(Transaction obj) {
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement ps = getConnection().prepareStatement(Requests.ADD_TRANSACTION);
            ps.setInt(1, obj.getPlayer().getId());
            ps.setString(2, obj.getTransactionType().name());
            ps.setDouble(3, obj.getMoneyCount());
            ps.executeUpdate();
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Количество всех транзакций в базе
     *
     * @return количество транзакций
     */
    @Override
    public int getAllCount() {
        int res = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(Requests.GET_TRANSACTIONS_COUNT)) {
            try (ResultSet set = ps.executeQuery()) {
                if (set.next()) {
                    res = set.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Получить список всех транзакций
     *
     * @return список транзакций
     */
    @Override
    public List<Transaction> getAll() {
        List<Transaction> res = new ArrayList<>();
        try {
            PreparedStatement ps = getConnection().prepareStatement(Requests.GET_ALL_TRANSACTIONS);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                res.add(set.getObject(1, Transaction.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Удалить транзакции из базы
     *
     * @param player_id id игрока, кому принадлежат транзакции
     */
    @Override
    public void delete(Integer player_id) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(Requests.DELETE_TRANSACTIONS_BY_PLAYER_ID);
            ps.setInt(1, player_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении транзакций игрока");
        }
    }
}
