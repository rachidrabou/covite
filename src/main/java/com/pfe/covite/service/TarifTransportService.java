package com.pfe.covite.service;

import com.pfe.covite.domain.TarifTransport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TarifTransport}.
 */
public interface TarifTransportService {

    /**
     * Save a tarifTransport.
     *
     * @param tarifTransport the entity to save.
     * @return the persisted entity.
     */
    TarifTransport save(TarifTransport tarifTransport);

    /**
     * Get all the tarifTransports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TarifTransport> findAll(Pageable pageable);

    /**
     * Get the "id" tarifTransport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarifTransport> findOne(Long id);

    /**
     * Delete the "id" tarifTransport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
