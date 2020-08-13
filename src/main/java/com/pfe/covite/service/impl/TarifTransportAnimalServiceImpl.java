package com.pfe.covite.service.impl;

import com.pfe.covite.service.TarifTransportAnimalService;
import com.pfe.covite.domain.TarifTransportAnimal;
import com.pfe.covite.repository.TarifTransportAnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifTransportAnimal}.
 */
@Service
@Transactional
public class TarifTransportAnimalServiceImpl implements TarifTransportAnimalService {

    private final Logger log = LoggerFactory.getLogger(TarifTransportAnimalServiceImpl.class);

    private final TarifTransportAnimalRepository tarifTransportAnimalRepository;

    public TarifTransportAnimalServiceImpl(TarifTransportAnimalRepository tarifTransportAnimalRepository) {
        this.tarifTransportAnimalRepository = tarifTransportAnimalRepository;
    }

    /**
     * Save a tarifTransportAnimal.
     *
     * @param tarifTransportAnimal the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TarifTransportAnimal save(TarifTransportAnimal tarifTransportAnimal) {
        log.debug("Request to save TarifTransportAnimal : {}", tarifTransportAnimal);
        return tarifTransportAnimalRepository.save(tarifTransportAnimal);
    }

    /**
     * Get all the tarifTransportAnimals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TarifTransportAnimal> findAll(Pageable pageable) {
        log.debug("Request to get all TarifTransportAnimals");
        return tarifTransportAnimalRepository.findAll(pageable);
    }

    /**
     * Get one tarifTransportAnimal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TarifTransportAnimal> findOne(Long id) {
        log.debug("Request to get TarifTransportAnimal : {}", id);
        return tarifTransportAnimalRepository.findById(id);
    }

    /**
     * Delete the tarifTransportAnimal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TarifTransportAnimal : {}", id);
        tarifTransportAnimalRepository.deleteById(id);
    }
}
