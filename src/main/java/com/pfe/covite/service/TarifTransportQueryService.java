package com.pfe.covite.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pfe.covite.domain.TarifTransport;
import com.pfe.covite.domain.*; // for static metamodels
import com.pfe.covite.repository.TarifTransportRepository;
import com.pfe.covite.service.dto.TarifTransportCriteria;

/**
 * Service for executing complex queries for {@link TarifTransport} entities in the database.
 * The main input is a {@link TarifTransportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifTransport} or a {@link Page} of {@link TarifTransport} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifTransportQueryService extends QueryService<TarifTransport> {

    private final Logger log = LoggerFactory.getLogger(TarifTransportQueryService.class);

    private final TarifTransportRepository tarifTransportRepository;

    public TarifTransportQueryService(TarifTransportRepository tarifTransportRepository) {
        this.tarifTransportRepository = tarifTransportRepository;
    }

    /**
     * Return a {@link List} of {@link TarifTransport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifTransport> findByCriteria(TarifTransportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifTransport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifTransport> findByCriteria(TarifTransportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifTransportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifTransportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifTransport> createSpecification(TarifTransportCriteria criteria) {
        Specification<TarifTransport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifTransport_.id));
            }
            if (criteria.getService() != null) {
                specification = specification.and(buildStringSpecification(criteria.getService(), TarifTransport_.service));
            }
            if (criteria.getVehicule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehicule(), TarifTransport_.vehicule));
            }
            if (criteria.getNombreDePersonne() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreDePersonne(), TarifTransport_.nombreDePersonne));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistance(), TarifTransport_.distance));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifTransport_.prix));
            }
        }
        return specification;
    }
}
