package com.maximyasn.data;

import com.maximyasn.core.entities.Player;


import java.util.HashSet;


/** Класс, представляющий собой хранилище игроков
 *  текущей сессии. Данные организованы в виде
 *  HashSet. Данные хранятся только во время работы приложения,
 *  очищаясь при его повторном запуске.
 * */

public class Players {

    /** Структура данных, представляющая хранилище событий. */
    private static HashSet<Player> players = new HashSet<>();

    private Players() {}


    /** Метод, возвращающий список игроков текущей сессии. */
    public static HashSet<Player> getPlayers() {
        return players;
    }

    /**
     * Метод для добавления игрока во множество игроков
     * текущей сессии.
     * @param player игрок для добавления в хранилище.
     * */
    public static void addPlayer(Player player) {
        if(player != null) {
            players.add(player);
        } else throw new NullPointerException("Player is null!");
    }


    /**
     * Метод для подтвеждения уникальности игрока в хранилище.
     * Необходим для избежания перезаписи игрока при совпадении имен.
     * @param name имя, проверяющееся на уникальность
     * @return логическое значение - результат работы метода.
     */
    public static boolean validateNewPlayer(String name) {
        boolean res = false;
        for (Player player : players) {
            if(player.getName().equals(name)) {
                return res;
            }
        }
        res = true;
        return res;
    }
}
