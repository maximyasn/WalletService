package com.maximyasn.core.services.in;


import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.exceptions.NameValidationException;
import com.maximyasn.core.entities.exceptions.PasswordValidationException;
import com.maximyasn.core.entities.exceptions.PlayerExsistsException;
import com.maximyasn.core.repo.PlayerRepo;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    PlayerService playerService;
    PlayerRepo playerRepo;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws PlayerExsistsException, NameValidationException, PasswordValidationException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:2345/wallet_service_database",
                    "maximyasn",
                    "maxim1100"
            );
        } catch (SQLException e) {
            System.out.println("Соединение не установлено");
        }
        playerRepo = new PlayerRepo(connection);
        playerService = new PlayerService(playerRepo);
    }

    @Test
    @DisplayName("Тест на регистрацию нового игрока")
    void registerNewPlayerTest() {
        try {
            Player player = playerService.registerNewPlayer("TestUser", "TestUser123");
            Player player1 = new Player("TestUser", "TestUser123");
            assertEquals(player1, player);
        } catch (NameValidationException | PlayerExsistsException | PasswordValidationException e) {
            System.out.println(e.getClass().getName());
        }
    }

}