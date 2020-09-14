package com.pfe.covite.service.impl;

import com.pfe.covite.service.VehiculeService;
import com.pfe.covite.domain.Vehicule;
import com.pfe.covite.repository.VehiculeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Vehicule}.
 */
@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

    private final Logger log = LoggerFactory.getLogger(VehiculeServiceImpl.class);

    private final VehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Save a vehicule.
     *
     * @param vehicule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Vehicule save(Vehicule vehicule) {
        log.debug("Request to save Vehicule : {}", vehicule);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Get all the vehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vehicule> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicules");
        return vehiculeRepository.findAll(pageable);
    }


    /**
     *  Get all the vehicules where Livreur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Vehicule> findAllWhereLivreurIsNull() {
        log.debug("Request to get all vehicules where Livreur is null");
        return StreamSupport
            .stream(vehiculeRepository.findAll().spliterator(), false)
            .filter(vehicule -> vehicule.getLivreur() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one vehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> findOne(Long id) {
        log.debug("Request to get Vehicule : {}", id);
        return vehiculeRepository.findById(id);
    }

    /**
     * Delete the vehicule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicule : {}", id);
        vehiculeRepository.deleteById(id);
    }
}
