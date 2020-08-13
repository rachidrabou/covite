package com.pfe.covite.service;

import com.pfe.covite.domain.TarifLivraison;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TarifLivraison}.
 */
public interface TarifLivraisonService {

    /**
     * Save a tarifLivraison.
     *
     * @param tarifLivraison the entity to save.
     * @return the persisted entity.
     */
    TarifLivraison save(TarifLivraison tarifLivraison);

    /**
     * Get all the tarifLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TarifLivraison> findAll(Pageable pageable);

    /**
     * Get the "id" tarifLivraison.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarifLivraison> findOne(Long id);

    /**
     * Delete the "id" tarifLivraison.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
