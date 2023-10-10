package com.maximyasn.core.services.in;


import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    Player player = Mockito.mock(Player.class);
    Transaction transaction = Mockito.mock(Transaction.class);

    @Test
    void checkPlayersBalanceTest() {
        Mockito.when(player.getBalance()).thenReturn(new BigDecimal(0));

        assertEquals(player.getBalance(), BigDecimal.ZERO);
    }


    @Test
    void getPlayersTransactionHistoryTest(){
        List<Transaction> transactions = new ArrayList<>();

        Mockito.when(player.getTransactionsHistory()).thenReturn(transactions);
        assertEquals(0, player.getTransactionsHistory().size());

        transactions.add(transaction);
        assertEquals(1, player.getTransactionsHistory().size());
    }

}