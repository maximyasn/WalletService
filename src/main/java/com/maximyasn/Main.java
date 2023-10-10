package com.maximyasn;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.services.in.SessionService;

/**
 * Точка входа в приложение. Переменная stop
 * метода main необходима для идентификации
 * желания пользователя завершить программу. Она
 * меняет свое значение при вводе в консоль
 * из метода sessionService.playersInterface() слова exit,
 * тем самым останавливая цикл.
 * Переменная player в цикле может иметь значение null,
 * что говорит о вводе exit из метода sessionService.startSession().
 */
public class Main {

    public static void main(String[] args) {
        boolean stop = false;
        SessionService sessionService = new SessionService();
        while (!stop) {
            Player player = sessionService.startSession();
            if(player == null) {
                System.out.println("До свидания!");
                break;
            }
            stop = sessionService.playersInterface(player);
        }
    }
}