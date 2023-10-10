package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    SessionService sessionService = Mockito.mock(SessionService.class);
    Player player = Mockito.mock(Player.class);

    @Test
    void startSessionTest() {
        Mockito.when(sessionService.startSession()).thenReturn(player);

        assertEquals(sessionService.startSession(), player);
    }

    @Test
    void playersInterfaceTest() {
        Mockito.when(sessionService.playersInterface(player)).thenReturn(true);

        assertTrue(sessionService.playersInterface(player));
    }

}