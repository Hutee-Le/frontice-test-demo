package vn.edu.likelion.front_ice.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<E,I,O> {
    Optional<O> create(I t);

    Optional<O> updateInfo(String id, I i);

    List<O> saveAll(List<E> ts);

    void delete(String id);

    void deleteAll(List<String> listId);

    Optional<O> findById(String id);

    List<O> findAll();
}
