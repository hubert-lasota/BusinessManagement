package org.hubert_lasota.BusinessManagement.repository;


import java.util.List;
import java.util.Optional;

public interface Repository <T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    Optional<List<T>> findAll();

    void delete(ID id);

    Long count();
}
