package com.maximyasn.data;

import com.maximyasn.core.entities.enums.EventStatus;

import java.util.*;

/**
 * Класс, представляющий собой хранилище для журналирования
 * процессов приложения. Данные организованы в виде
 * LinkedHashMap. Данные хранятся только во время работы приложения,
 * очищаясь при его повторном запуске.
 */
public class Journal {


    /** Структура данных, представляющая хранилище событий */
    private static LinkedHashMap<String, EventStatus> JOURNAL = new LinkedHashMap<>();

    private Journal() {}


    /** Метод для чтения журнала событий */
    public static void journalOnRead() {
        JOURNAL.entrySet().forEach(System.out::println);
    }

    /** Метод для добавления в журнал нового события */
    public static void put(String s, EventStatus status) {
        if(s != null && !s.isEmpty() && status != null) {
            JOURNAL.put(s, status);
        }
    }
}
