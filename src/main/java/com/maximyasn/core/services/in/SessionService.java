package com.maximyasn.core.services.in;

import com.maximyasn.core.entities.Player;
import com.maximyasn.core.entities.Transaction;
import com.maximyasn.core.entities.enums.EventStatus;
import com.maximyasn.core.entities.enums.TransactionType;
import com.maximyasn.core.entities.exceptions.*;
import com.maximyasn.data.Journal;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

/**
 * Класс, представляющий собой текущую сессию работы приложения.
 * Данные сессии хранятся только во время ее работы.
 */
public class SessionService {


    private final Scanner scanner = new Scanner(System.in);

    /** Стоп-слово, ввод которого завершает текущую сессию */
    private final String exit = "exit";

    /**
     * Метод, дающий начало новой сессии. Игрок выбирает
     * дейстие с помощью ввода в консоль значений 1 или 2
     * (соответственно, войти или зарегистрироваться).
     * @return новый игрок, который будет передан в основной метод
     * сессии, либо же существующий в текущей сессии игрок.
     */
    public Player startSession() {
        Journal.put("Сессия открыта", EventStatus.SUCCESS);

        String input = "";


        while (!input.trim().equals(exit)) {

            System.out.println("""
                    Вас приветствует Wallet-Service!
                    Выберите дальнейшее действие:
                    1 - Войти в свой аккаунт
                    2 - Зарегистрироваться
                    На любом этапе для выхода из приложения
                    введите exit.""");

            input = scanner.nextLine().trim();

            if (input.trim().equals("1")) {
                try {
                    return enterIntoAccount();
                } catch (NotAuthorizedException e) {
                    System.out.println(e.getMessage());
                    Journal.put("Пользователь авторизовался", EventStatus.FAIlED);
                }

            } else if (input.equals("2")) {
                try {
                    return registration();
                } catch (PlayerExsistsException | NameValidationException | PasswordValidationException e) {
                    System.out.println(e.getMessage());
                    Journal.put("Пользователь ... зарегистрировал новый аккаунт", EventStatus.FAIlED);
                }
            } else {
                if(input.trim().equals("exit")) {
                    System.out.println("До свидания!");
                    return null;
                } else {
                    System.out.println("\nНекорректные данные!\n");
                }
            }
        }
        return null;
    }

    /**
     * Метод, отвечающий за ход сессии. Пользователю доступны для выбора
     * 6 вариантов действий, которые он выбирает путем ввода в консоль
     * соответствующих числовых значений:
     * 1 - Снятие средств;
     * 2 - Пополнение баланса;
     * 3 - Просмотр истории операций текущего игрока;
     * 4 - Сменить текущего пользователя. Данная операция возвращает управление
     *     методу startSession() в цикле метода main благодаря возврату логического значения false локальной
     *     переменной stop.
     * 5 - Посмотреть баланс текущего пользователя;
     * 6 - Посмотреть журнал событий текущей сессии.
     * @param player игрок, полученный в результате исполнения метода startSession() и
     *               от чьего имени ведется управление интерфейсом в консоли
     * @return логическое значение, позволяющее отличить намерение игрока выйти
     *         из приложения от желания сменить пользователя. Ввод "exit" в консоли
     *         прерывает цикл сессии, после чего меняет значение переменной stop на true.
     *         Возврат данным методом значения true прерывает цикл метода main и завершает
     *         выполнение программы.
     */
    public boolean playersInterface(Player player) {
        Journal.put("Пользователь " + player.getName() + " вошел в интерфейс пользователя", EventStatus.SUCCESS);
        String input = "";
        boolean stop = false;

        while (!input.equals(exit)) {
            System.out.println("Интерфейс игрока " + player.getName() + "\n");
            System.out.println("""
                    1. Cнятие средств.
                    2. Пополнить баланс.
                    3. Просмотр истории операций.
                    4. Сменить пользователя.
                    5. Посмотреть баланс.
                    6. Посмотреть журнал.
                                        
                    Выберите действие:""");
            input = scanner.nextLine().trim();

            Journal.put("Пользователь " + player.getName() + " выбрал действие " + input, EventStatus.SUCCESS);

            switch (input) {
                case "1" -> {
                    try {
                        withdrawal(player);
                    } catch (NegativeBalanceException e) {
                        System.out.println("На балансе недостаточно средств!");
                        Journal.put("Транзакция прошла успешно", EventStatus.FAIlED);
                        continue;
                    } catch (TransactionExistsException e) {
                        System.out.println("Транзакция с таким id уже существует!");
                        Journal.put("Транзакция прошла успешно", EventStatus.FAIlED);
                        continue;
                    } catch (NumberFormatException e) {
                        Journal.put("Пользователь " + player.getName() + "ввел сумму", EventStatus.FAIlED);
                        System.out.println("Неверный формат ввода данных.");
                        continue;
                    }
                    System.out.println("""
                            Транзакция успешно совершена!\n\n""");
                }
                case "2" -> {
                    try {
                        refill(player);
                    } catch (TransactionExistsException | NegativeBalanceException e) {
                        Journal.put("Транзакция прошла успешно", EventStatus.FAIlED);
                        System.out.println("Транзакция с таким id уже существует!");
                        continue;
                    } catch (NumberFormatException e) {
                        Journal.put("Пользователь " + player.getName() + "ввел сумму", EventStatus.FAIlED);
                        System.out.println("Неверный формат ввода данных.");
                        continue;
                    }
                    System.out.println("""
                            Транзакция успешно завершена!\n\n""");
                }
                case "3" -> PlayerService.getPlayersTransactionHistory(player);
                case "4" -> {
                    Journal.put("Пользователь " + player.getName() + " вышел из аккаунта", EventStatus.SUCCESS);
                    return stop;
                }
                case "5" -> PlayerService.checkCurrentBalance(player);
                case "6" -> {
                    Journal.journalOnRead();
                    Journal.put("Пользователь " + player.getName() + " открыл журнал", EventStatus.SUCCESS);
                }
                default -> {
                    if (input.equals("exit")) {
                        Journal.put("Пользователь " + player.getName() + " выбрал действие " + input + " : ", EventStatus.SUCCESS);
                        System.out.println("До свидания!");
                    } else {
                        Journal.put("Пользователь " + player.getName() + " выбрал действие " + input, EventStatus.FAIlED);
                        System.out.println("Введите корректные данные!\n\n\n");
                    }
                }
            }
        }
        stop = true;
        return stop;
    }

    /**
     * Сгятие средств со счета.
     * @param player текущий игрок
     * @throws NegativeBalanceException недостаточно средств на балансе
     * @throws TransactionExistsException id транзакции не уникален
     */
    private void withdrawal (Player player) throws NegativeBalanceException, TransactionExistsException, NumberFormatException {
        System.out.println("\n");
        System.out.println("""
                        Введите сумму, которую желаете снять:""");
        String inputWith = scanner.nextLine().trim();
        BigDecimal sum;

        sum = BigDecimal.valueOf(Double.parseDouble(inputWith));
        Journal.put("Пользователь " + player.getName() + " ввел сумму", EventStatus.SUCCESS);

        UUID id = player.getTransactionID();
        Transaction transaction = new Transaction(player, TransactionType.DEBIT, sum, id);
        Journal.put("Транзакция создалась", EventStatus.SUCCESS);

        TransactionService.doTransaction(player, transaction);
        Journal.put("Транзакция прошла успешно", EventStatus.SUCCESS);
    }

    /**
     * Пополнение средств
     * @param player текущий игрок
     * @throws TransactionExistsException id транзакции не уникален
     */
    private void refill(Player player) throws NumberFormatException, NegativeBalanceException, TransactionExistsException {
        System.out.println("\n");
        System.out.println("""
                        Введите сумму, которую желаете зачислить на баланс:""");
        String inputRefill = scanner.nextLine().trim();

        BigDecimal sum;
        sum = BigDecimal.valueOf(Double.parseDouble(inputRefill));
        Journal.put("Пользователь " + player.getName() + "ввел сумму", EventStatus.SUCCESS);

        UUID id = player.getTransactionID();
        Transaction transaction = new Transaction(player, TransactionType.CREDIT, sum, id);
        Journal.put("Транзакция создалась", EventStatus.SUCCESS);

        TransactionService.doTransaction(player, transaction);
        Journal.put("Транзакция прошла успешно", EventStatus.SUCCESS);
    }

    /**
     * Вход в пользовательский аккаунт
     * @return текущий игрок
     * @throws NotAuthorizedException пользователь не найден
     */
    private Player enterIntoAccount() throws NotAuthorizedException {
        Journal.put("Пользователь пытается войти в свой аккаунт", EventStatus.SUCCESS);

        System.out.println("""
                        Введите имя пользователя и пароль.""");

        String name = scanner.nextLine().trim();
        String password = scanner.nextLine().trim();

        Player player = RegistrationService.authenticatePlayer(name, password);
        Journal.put("Пользователь авторизовался", EventStatus.SUCCESS);

        return player;
    }

    /**
     * Регистрация нового пользователя
     * @return зарегистрированный пользователь
     * @throws PlayerExsistsException имя пользователя не является уникальным
     * @throws NameValidationException ошибка валидации имени пользователя
     * @throws PasswordValidationException ошибка валидации пароля пользователя
     */
    private Player registration() throws PlayerExsistsException, NameValidationException, PasswordValidationException{
        Journal.put("Пользователь пытается создать новый аккаунт", EventStatus.SUCCESS);
        String name;
        String password;
        System.out.print("""
                        Введите имя нового игрока. Оно должно
                        состоять только из латинских букв любого регистра
                        и цифр, иметь длину 2-20 символов, начиная с буквы:\n""");


        name = scanner.nextLine().trim();
        System.out.print("""
                        \nВведите пароль для нового икрока. Он должен
                        содержать минимум 8 символов и должен состоять из
                        латинских букв и цифр, должен содержать минимум одну
                        заглавную букву и может содержать спецсимволы:\n""");
        password = scanner.nextLine().trim();

        Player player = RegistrationService.registerNewPlayer(name, password);
        Journal.put("Пользователь " + player.getName() + " зарегистрировал новый аккаунт", EventStatus.SUCCESS);
        return player;
    }
}
