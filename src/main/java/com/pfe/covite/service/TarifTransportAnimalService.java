package com.pfe.covite.service;

import com.pfe.covite.domain.TarifTransportAnimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TarifTransportAnimal}.
 */
public interface TarifTransportAnimalService {

    /**
     * Save a tarifTransportAnimal.
     *
     * @param tarifTransportAnimal the entity to save.
     * @return the persisted entity.
     */
    TarifTransportAnimal save(TarifTransportAnimal tarifTransportAnimal);

    /**
     * Get all the tarifTransportAnimals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TarifTransportAnimal> findAll(Pageable pageable);

    /**
     * Get the "id" tarifTransportAnimal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarifTransportAnimal> findOne(Long id);

    /**
     * Delete the "id" tarifTransportAnimal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
