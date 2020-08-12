package com.pfe.covite.service;

import com.pfe.covite.domain.Livreur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Livreur}.
 */
public interface LivreurService {

    /**
     * Save a livreur.
     *
     * @param livreur the entity to save.
     * @return the persisted entity.
     */
    Livreur save(Livreur livreur);

    /**
     * Get all the livreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Livreur> findAll(Pageable pageable);

    /**
     * Get the "id" livreur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Livreur> findOne(Long id);

    /**
     * Delete the "id" livreur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
