package com.pfe.covite.service.impl;

import com.pfe.covite.service.TarifTransportService;
import com.pfe.covite.domain.TarifTransport;
import com.pfe.covite.repository.TarifTransportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifTransport}.
 */
@Service
@Transactional
public class TarifTransportServiceImpl implements TarifTransportService {

    private final Logger log = LoggerFactory.getLogger(TarifTransportServiceImpl.class);

    private final TarifTransportRepository tarifTransportRepository;

    public TarifTransportServiceImpl(TarifTransportRepository tarifTransportRepository) {
        this.tarifTransportRepository = tarifTransportRepository;
    }

    /**
     * Save a tarifTransport.
     *
     * @param tarifTransport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TarifTransport save(TarifTransport tarifTransport) {
        log.debug("Request to save TarifTransport : {}", tarifTransport);
        return tarifTransportRepository.save(tarifTransport);
    }

    /**
     * Get all the tarifTransports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TarifTransport> findAll(Pageable pageable) {
        log.debug("Request to get all TarifTransports");
        return tarifTransportRepository.findAll(pageable);
    }

    /**
     * Get one tarifTransport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TarifTransport> findOne(Long id) {
        log.debug("Request to get TarifTransport : {}", id);
        return tarifTransportRepository.findById(id);
    }

    /**
     * Delete the tarifTransport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TarifTransport : {}", id);
        tarifTransportRepository.deleteById(id);
    }
}
