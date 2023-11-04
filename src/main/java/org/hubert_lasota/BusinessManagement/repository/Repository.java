package org.hubert_lasota.BusinessManagement.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T> {

    T save(T entity);

    Optional<T> findById(Long id);

    Optional<List<T>> findAll();

    T update(Long id, T entityUpdater);

    void delete(Long id);

    Long count();
}
