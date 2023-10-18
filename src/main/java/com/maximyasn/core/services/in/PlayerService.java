package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.core.entities.exceptions.NameValidationException;
import com.maximyasn.core.entities.exceptions.NotAuthorizedException;
import com.maximyasn.core.entities.exceptions.PasswordValidationException;
import com.maximyasn.core.entities.exceptions.PlayerExsistsException;
import com.maximyasn.core.repo.RepoInterface;
import com.maximyasn.data.Journal;

import java.util.regex.Pattern;

/**
 * Сервисный класс, предоставляющий операции над
 * экземплярами классов игроков
 */
public class PlayerService implements DefaultService<Player>{

    private final RepoInterface<Player> repository;

    public PlayerService(RepoInterface<Player> repository) {
        this.repository = repository;
    }


    /**
     * Метод, выводящий в консоль текущий баланс игрока
     * @param playerName игрок, с чьего аккаунта ведутся действия
     */
    public void checkCurrentBalance(String playerName) {
        System.out.println(repository.getByName(playerName).getBalance());
    }

    /**
     * Метод, выводящий в консоль текущую историю транзакций игрока
     * @param name игрок, с чьего аккаунта ведутся действия
     */
    public void getPlayersTransactionHistory(String name) {
        Integer id = repository.getByName(name).getId();
        repository.getTransactionHistory(id).forEach(System.out::println);
    }

    /**
     * Метод, регистрирующий нового игрока
     * @param userName имя нового игрока, должно быть уникальным для текущей сессии
     * @param userPass пароль нового игрока
     * @return новый игрок, в дальнейшем выполняющий действия в аккаунте
     * @throws NameValidationException ошибка валидации имени
     * @throws PlayerExsistsException ошибка, возникающая при попытке создать пользователя с уже существующим
     * для данной сессии именем
     * @throws PasswordValidationException ошибка валидации пароля
     */
    public Player registerNewPlayer(String userName, String userPass) throws NameValidationException, PlayerExsistsException, PasswordValidationException {

        if (!validateName(userName)) {
            Journal.put("Ввод имени при регистрации", EventStatus.FAIlED);
            throw new NameValidationException("Неверный формат имени, попробуйте еще раз.\n\n\n");

        }

        if (!validatePassword(userPass)) {
            Journal.put("Ввод пароля при регистрации", EventStatus.FAIlED);
            throw new PasswordValidationException("Неверный формат пароля, попробуйте еще раз.\n\n\n");
        }


        Player player = new Player(userName, userPass);
        repository.add(player);
        Journal.put("Игрок " + player.getName() + " добавлен в список игроков", EventStatus.SUCCESS);
        return player;
    }

    /**
     * Метод, аутентифицирующий уже существующего пользователя в текущей сессии
     * @param userName имя существующего пользователя
     * @return - игрок, ранее зарегистрированный в текущей сессии
     * @throws NotAuthorizedException ошибка, возникающая при попытке аутентификации
     * несуществующего в текущей сессии игрока
     */
    public Player authenticatePlayer(String userName, String password) throws NotAuthorizedException {

        Player player = repository.getByNameAndPass(userName, password);

        if (player != null) {
            Journal.put("Игрок с именем " + userName + " найден", EventStatus.SUCCESS);
            return player;
        } else {
            Journal.put("Игрок с именем " + userName + " найден", EventStatus.FAIlED);
            throw new NotAuthorizedException("Пользователь не найден! Возможно вы ввели" +
                                             " неверные данные.\n\n");
        }
    }


    /**
     * Метод, проверяющий имя игрока на соответствие шаблону
     * @param name проверяемое имя игрока
     * @return true/false - результат соответствия
     */
    private boolean validateName(String name) {
        return Pattern.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$", name);
    }

    /**
     * Метод, проверяющий пароль игрока на соответствие шаблону
     * @param password проверяемый пароль игрока
     * @return true/false - результат соответствия
     */
    private boolean validatePassword(String password) {
        return Pattern.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", password);
    }


}
