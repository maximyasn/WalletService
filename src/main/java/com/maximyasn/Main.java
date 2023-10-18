package com.maximyasn;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.repo.PlayerRepo;
import com.maximyasn.core.repo.TransactionRepo;
import com.maximyasn.core.services.in.PlayerService;
import com.maximyasn.core.services.in.SessionService;
import com.maximyasn.core.services.in.TransactionService;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/db/config/liquibase.properties")) {
            prop.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    prop.getProperty("url"),
                    prop.getProperty("username"),
                    prop.getProperty("password")
            );
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        }
        try {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (LiquibaseException e) {
            System.out.println("Ошибка liquibase: " + e.getCause().toString());
        }

        PlayerRepo playerRepo = new PlayerRepo(connection);
        PlayerService playerService = new PlayerService(playerRepo);
        TransactionService transactionService = new TransactionService(new TransactionRepo(connection));
        SessionService sessionService = new SessionService(playerService, transactionService, playerRepo);

        boolean stop = false;
        while (!stop) {
            Player player = sessionService.startSession();
            if (player == null) {
                break;
            }
            stop = sessionService.playersInterface(player);
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
            }
        }
    }
}