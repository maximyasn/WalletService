package com.maximyasn.core.services.in;

import com.maximyasn.Main;
import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.exceptions.NameValidationException;
import com.maximyasn.core.entities.exceptions.NotAuthorizedException;
import com.maximyasn.core.entities.exceptions.PasswordValidationException;
import com.maximyasn.core.entities.exceptions.PlayerExsistsException;
import com.maximyasn.data.Players;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
    RegistrationService registrationService = Mockito.mock(RegistrationService.class);


    @Test
    void registerNewPlayerTest() throws PlayerExsistsException, NameValidationException, PasswordValidationException {
        String name = "Иван";
        String password = "Иван12345";

        Mockito.when(registrationService.registerNewPlayer(name, password)).thenReturn(new Player(name, password));

        Player player = new Player(name, password);

        assertEquals(registrationService.registerNewPlayer(name, password), player);
    }

    @Test
    void registerNewPlayerExceptions() throws PlayerExsistsException, NameValidationException, PasswordValidationException {
        String name = "Ivan";
        String password = "p";

        Mockito.when(registrationService.registerNewPlayer(name, password)).thenThrow(PasswordValidationException.class);

        assertThrows(PasswordValidationException.class, () -> registrationService.registerNewPlayer(name, password));

        String name1 = "";
        String password1 = "Ivan12345";

        Mockito.when(registrationService.registerNewPlayer(name1, password1)).thenThrow(NameValidationException.class);

        assertThrows(NameValidationException.class, ()-> registrationService.registerNewPlayer(name1, password1));
    }

    @Test
    void registerNewPlayerExistException() throws PlayerExsistsException, NameValidationException, PasswordValidationException {

        Mockito.when(registrationService.registerNewPlayer("Иван", "Иванович")).thenThrow(PlayerExsistsException.class);

        assertThrows(PlayerExsistsException.class, () -> registrationService.registerNewPlayer("Иван", "Иванович"));
    }

    @Test
    void authorizePlayerExceptionTest() throws NotAuthorizedException {
        Mockito.when(registrationService.authorizePlayer("Иван", "Иванович")).thenThrow(NotAuthorizedException.class);

        assertThrows(NotAuthorizedException.class, () -> registrationService.authorizePlayer("Иван", "Иванович"));
    }

}