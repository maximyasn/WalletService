package com.maximyasn.core.repo;

import com.maximyasn.core.entities.Transaction;

import java.util.List;


public interface RepoInterface<T> {
     T getById(int id);

     void add(T obj);

     int getAllCount();

     List<T> getAll();

     default T getByName(String name) {
          return null;
     }

     default void update(String name, Double sum) {

     }

     default T getByNameAndPass(String name, String pass) {
          return null;
     }

     default List<Transaction> getTransactionHistory(Integer id) {
          return null;
     }

     void delete(Integer id);

}
