package com.maximyasn.core.repo;

import com.maximyasn.core.entities.Player;
import com.maximyasn.data.Players;

import java.util.HashMap;

public class PlayerRepo {

    /** Метод, возвращающий список игроков текущей сессии. */
    public static HashMap<String, Player> getPlayers() {
        return Players.getPlayers();
    }

    /**
     * Метод для добавления игрока во множество игроков
     * текущей сессии.
     * @param player игрок для добавления в хранилище.
     * */
    public static void addPlayer(Player player) {
        Players.addPlayer(player);
    }


    public static boolean checkForPlayerMatch(String name) {
        return getPlayers().containsKey(name);
    }

    public static Player getPlayerByName(String name) {
        return getPlayers().get(name);
    }
}
