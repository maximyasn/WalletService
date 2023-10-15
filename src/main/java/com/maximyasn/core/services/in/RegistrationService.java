package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.core.entities.exceptions.NameValidationException;
import com.maximyasn.core.entities.exceptions.NotAuthorizedException;
import com.maximyasn.core.entities.exceptions.PasswordValidationException;
import com.maximyasn.core.entities.exceptions.PlayerExsistsException;
import com.maximyasn.core.repo.PlayerRepo;
import com.maximyasn.data.Journal;

import java.util.regex.Pattern;

/**
 * Сервисный класс, предоставляющий методы по регистрации
 * и авторизации игрока
 */

public class RegistrationService {


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
    public static Player registerNewPlayer(String userName, String userPass) throws NameValidationException, PlayerExsistsException, PasswordValidationException {

        String name = userName;
        if (!validateName(name)) {
            Journal.put("Ввод имени при регистрации", EventStatus.FAIlED);
            throw new NameValidationException("Неверный формат имени, попробуйте еще раз.\n\n\n");

        } else if (PlayerRepo.checkForPlayerMatch(name)) {
            Journal.put("Ввод имени при регистрации", EventStatus.FAIlED);
            throw new PlayerExsistsException("Игрок с таким именем уже существует! Попробуйте еще раз.");
        }
        userName = name;

        String password = userPass;
        if (!validatePassword(password)) {
            Journal.put("Ввод пароля при регистрации", EventStatus.FAIlED);
            throw new PasswordValidationException("Неверный формат пароля, попробуйте еще раз.\n\n\n");
        }
        userPass = password;


        Player player = new Player(userName, userPass);
        PlayerRepo.addPlayer(player);
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
    public static Player authenticatePlayer(String userName, String password) throws NotAuthorizedException {
        boolean isPresent = PlayerRepo.checkForPlayerMatch(userName);
        boolean isPasswordCorrect = isPresent && PlayerRepo.getPlayerByName(userName).getPassword().equals(password);


        if (isPresent && isPasswordCorrect) {
            Journal.put("Игрок с именем " + userName + " найден", EventStatus.SUCCESS);
            return PlayerRepo.getPlayerByName(userName);
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
    private static boolean validateName(String name) {
        return Pattern.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$", name);
    }

    /**
     * Метод, проверяющий пароль игрока на соответствие шаблону
     * @param password проверяемый пароль игрока
     * @return true/false - результат соответствия
     */
    private static boolean validatePassword(String password) {
        return Pattern.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", password);
    }
}
