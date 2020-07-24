package com.pfe.covite.service;

import com.pfe.covite.domain.Commandes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Commandes}.
 */
public interface CommandesService {

    /**
     * Save a commandes.
     *
     * @param commandes the entity to save.
     * @return the persisted entity.
     */
    Commandes save(Commandes commandes);

    /**
     * Get all the commandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Commandes> findAll(Pageable pageable);

    /**
     * Get the "id" commandes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Commandes> findOne(Long id);

    /**
     * Delete the "id" commandes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
