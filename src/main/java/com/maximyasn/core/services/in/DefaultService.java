package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.exceptions.*;
import com.maximyasn.core.repo.RepoInterface;

public interface DefaultService<T> {
    default T authenticatePlayer(String name, String pass) throws NotAuthorizedException {
        return null;
    }

    default T registerNewPlayer(String name, String pass) throws NameValidationException, PlayerExsistsException, PasswordValidationException {
        return null;
    }

    default void getPlayersTransactionHistory(String name) {

    }

    default void checkCurrentBalance(String name) {

    }

    default void doTransaction(RepoInterface<Player> repo, Player player, T t) throws NegativeBalanceException, TransactionExistsException{

    }
}
