package com.pfe.covite.service.impl;

import com.pfe.covite.service.TarifLivraisonService;
import com.pfe.covite.domain.TarifLivraison;
import com.pfe.covite.repository.TarifLivraisonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifLivraison}.
 */
@Service
@Transactional
public class TarifLivraisonServiceImpl implements TarifLivraisonService {

    private final Logger log = LoggerFactory.getLogger(TarifLivraisonServiceImpl.class);

    private final TarifLivraisonRepository tarifLivraisonRepository;

    public TarifLivraisonServiceImpl(TarifLivraisonRepository tarifLivraisonRepository) {
        this.tarifLivraisonRepository = tarifLivraisonRepository;
    }

    /**
     * Save a tarifLivraison.
     *
     * @param tarifLivraison the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TarifLivraison save(TarifLivraison tarifLivraison) {
        log.debug("Request to save TarifLivraison : {}", tarifLivraison);
        return tarifLivraisonRepository.save(tarifLivraison);
    }

    /**
     * Get all the tarifLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TarifLivraison> findAll(Pageable pageable) {
        log.debug("Request to get all TarifLivraisons");
        return tarifLivraisonRepository.findAll(pageable);
    }

    /**
     * Get one tarifLivraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TarifLivraison> findOne(Long id) {
        log.debug("Request to get TarifLivraison : {}", id);
        return tarifLivraisonRepository.findById(id);
    }

    /**
     * Delete the tarifLivraison by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TarifLivraison : {}", id);
        tarifLivraisonRepository.deleteById(id);
    }
}
