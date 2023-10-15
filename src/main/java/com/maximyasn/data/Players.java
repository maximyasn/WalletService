package com.maximyasn.data;

import com.maximyasn.core.entities.Player;


import java.util.HashMap;


/** Класс, представляющий собой хранилище игроков
 *  текущей сессии. Данные организованы в виде
 *  HashSet. Данные хранятся только во время работы приложения,
 *  очищаясь при его повторном запуске.
 * */

public class Players {

    /** Структура данных, представляющая хранилище событий. */
    private static HashMap<String, Player> players = new HashMap<>();

    private Players() {}


    /** Метод, возвращающий список игроков текущей сессии. */
    public static HashMap<String, Player> getPlayers() {
        return players;
    }

    /**
     * Метод для добавления игрока во множество игроков
     * текущей сессии.
     * @param player игрок для добавления в хранилище.
     * */
    public static void addPlayer(Player player) {
        if(player != null) {
            players.put(player.getName(), player);
        } else throw new NullPointerException("Player is null!");
    }


}
