package com.pfe.covite.service.impl;

import com.pfe.covite.service.CommandesService;
import com.pfe.covite.domain.Commandes;
import com.pfe.covite.repository.CommandesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commandes}.
 */
@Service
@Transactional
public class CommandesServiceImpl implements CommandesService {

    private final Logger log = LoggerFactory.getLogger(CommandesServiceImpl.class);

    private final CommandesRepository commandesRepository;

    public CommandesServiceImpl(CommandesRepository commandesRepository) {
        this.commandesRepository = commandesRepository;
    }

    /**
     * Save a commandes.
     *
     * @param commandes the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Commandes save(Commandes commandes) {
        log.debug("Request to save Commandes : {}", commandes);
        return commandesRepository.save(commandes);
    }

    /**
     * Get all the commandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Commandes> findAll(Pageable pageable) {
        log.debug("Request to get all Commandes");
        return commandesRepository.findAll(pageable);
    }

    /**
     * Get one commandes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Commandes> findOne(Long id) {
        log.debug("Request to get Commandes : {}", id);
        return commandesRepository.findById(id);
    }

    /**
     * Delete the commandes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commandes : {}", id);
        commandesRepository.deleteById(id);
    }
}
