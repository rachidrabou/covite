package com.pfe.covite.service.impl;

import com.pfe.covite.service.LivreurService;
import com.pfe.covite.domain.Livreur;
import com.pfe.covite.repository.LivreurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Livreur}.
 */
@Service
@Transactional
public class LivreurServiceImpl implements LivreurService {

    private final Logger log = LoggerFactory.getLogger(LivreurServiceImpl.class);

    private final LivreurRepository livreurRepository;

    public LivreurServiceImpl(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    /**
     * Save a livreur.
     *
     * @param livreur the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Livreur save(Livreur livreur) {
        log.debug("Request to save Livreur : {}", livreur);
        return livreurRepository.save(livreur);
    }

    /**
     * Get all the livreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Livreur> findAll(Pageable pageable) {
        log.debug("Request to get all Livreurs");
        return livreurRepository.findAll(pageable);
    }

    /**
     * Get one livreur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Livreur> findOne(Long id) {
        log.debug("Request to get Livreur : {}", id);
        return livreurRepository.findById(id);
    }

    /**
     * Delete the livreur by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Livreur : {}", id);
        livreurRepository.deleteById(id);
    }
}
