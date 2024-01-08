package org.hubert_lasota.BusinessManagement.repository;


import java.util.List;
import java.util.Optional;

/**
 * The {@code Repository} interface defines the basic operations for a generic repository
 * that stores entities of type {@code T} with identifiers of type {@code ID}.
 *
 * @param <T> The type of entities stored in the repository.
 * @param <ID> The type of identifiers for the entities.
 */
public interface Repository <T, ID> {

    /**
     * Saves an entity in the repository.
     *
     * @param entity The entity to be saved.
     * @return The saved entity.
     */
    T save(T entity);

    /**
     * Retrieves an entity by its identifier from the repository.
     *
     * @param id The identifier of the entity to be retrieved.
     * @return An {@link Optional} containing the retrieved entity, or an empty {@code Optional} if not found.
     */
    Optional<T> findById(ID id);

    /**
     * Retrieves a list of all entities in the repository.
     *
     * @return An {@code Optional} containing a list of all entities, or an empty {@code Optional} if the repository is empty.
     */
    Optional<List<T>> findAll();

    /**
     * Deletes an entity with the specified identifier from the repository.
     *
     * @param id The identifier of the
    entity to be deleted.
     */
    void delete(ID id);

    /**
     * Counts the total number of entities in the repository.
     *
     * @return The count of entities in the repository.
     */
    Long count();
}
