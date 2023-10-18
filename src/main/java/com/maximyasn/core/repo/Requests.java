package com.maximyasn.core.repo;

public class Requests {

    public static final String GET_ALL_PLAYERS = "select * from players";

    public static final String ADD_PLAYER =
            "insert into players(name, password, balance) values (?, ?, ?)";

    public static final String GET_PLAYER_BY_NAME =
            "select * from players where name = ?";

    public static final String GET_BY_NAME_AND_PASS =
            "select * from players where name = ? and password = ?";


    public static final String GET_PLAYER_BY_ID =
            "select * from players where id = ?";

    public static final String GET_PLAYERS_COUNT =
            "select count(*) from players";

    public static final String DELETE_PLAYER_BY_ID =
            "delete from players where id = ?";

//    -------------------------------------------------------------------------------------

    public static final String GET_TRANSACTIONS_COUNT
            = "select count(*) transactions";

    public static final String ADD_TRANSACTION =
            "insert into transactions(player_id, transaction_type, money_count) values (?, ?::trans_type, ?)";

    public static final String GET_TRANSACTION_BY_ID =
            "select * from transactions where id = ?";

    public static final String GET_ALL_TRANSACTIONS =
            "select * from transactions";

    public static final String UPDATE_PLAYERS_BALANCE_AFTER_TRANSACTION =
            "update players set balance = balance + ? " +
            "where name = ?";

    public static final String GET_PLAYERS_TRANSACTIONS =
            "select * from transactions where player_id = ?";

    public static final String DELETE_TRANSACTIONS_BY_PLAYER_ID =
            "delete from transactions where player_id = ?";




    private Requests() {}

}
