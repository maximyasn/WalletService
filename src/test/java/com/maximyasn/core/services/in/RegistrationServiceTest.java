package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.exceptions.NameValidationException;
import com.maximyasn.core.entities.exceptions.NotAuthorizedException;
import com.maximyasn.core.entities.exceptions.PasswordValidationException;
import com.maximyasn.core.entities.exceptions.PlayerExsistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {


    @Test
    @DisplayName("Проверка возврата методом игрока")
    void registerNewPlayerTest() throws PlayerExsistsException, NameValidationException, PasswordValidationException {
        String name = "Иван";
        String password = "Иван12345";

        Player player = new Player(name, password);

        assertEquals(RegistrationService.registerNewPlayer(name, password), player);
    }

    @Test
    @DisplayName("Проверка исключений валидации имени и пароля")
    void registerNewPlayerExceptions() {
        String name = "Ivan";
        String password = "p";

        assertThrows(PasswordValidationException.class, () -> RegistrationService.registerNewPlayer(name, password));


        String name1 = "";
        String password1 = "Ivan12345";


        assertThrows(NameValidationException.class, () -> RegistrationService.registerNewPlayer(name1, password1));


    }


    @Test
    @DisplayName("Проверка получения исключения при неавторизованном пользователе")
    void authorizePlayerExceptionTest() {
        String name = "Ivan";
        String pass = "Ivanovich1";

        assertThrows(NotAuthorizedException.class, () -> RegistrationService.authenticatePlayer(name, pass));
    }

}