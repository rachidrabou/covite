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

import com.pfe.covite.domain.TarifTransportAnimal;
import com.pfe.covite.domain.*; // for static metamodels
import com.pfe.covite.repository.TarifTransportAnimalRepository;
import com.pfe.covite.service.dto.TarifTransportAnimalCriteria;

/**
 * Service for executing complex queries for {@link TarifTransportAnimal} entities in the database.
 * The main input is a {@link TarifTransportAnimalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifTransportAnimal} or a {@link Page} of {@link TarifTransportAnimal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifTransportAnimalQueryService extends QueryService<TarifTransportAnimal> {

    private final Logger log = LoggerFactory.getLogger(TarifTransportAnimalQueryService.class);

    private final TarifTransportAnimalRepository tarifTransportAnimalRepository;

    public TarifTransportAnimalQueryService(TarifTransportAnimalRepository tarifTransportAnimalRepository) {
        this.tarifTransportAnimalRepository = tarifTransportAnimalRepository;
    }

    /**
     * Return a {@link List} of {@link TarifTransportAnimal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifTransportAnimal> findByCriteria(TarifTransportAnimalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifTransportAnimal> specification = createSpecification(criteria);
        return tarifTransportAnimalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifTransportAnimal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifTransportAnimal> findByCriteria(TarifTransportAnimalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifTransportAnimal> specification = createSpecification(criteria);
        return tarifTransportAnimalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifTransportAnimalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifTransportAnimal> specification = createSpecification(criteria);
        return tarifTransportAnimalRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifTransportAnimalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifTransportAnimal> createSpecification(TarifTransportAnimalCriteria criteria) {
        Specification<TarifTransportAnimal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifTransportAnimal_.id));
            }
            if (criteria.getService() != null) {
                specification = specification.and(buildStringSpecification(criteria.getService(), TarifTransportAnimal_.service));
            }
            if (criteria.getAnimal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnimal(), TarifTransportAnimal_.animal));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistance(), TarifTransportAnimal_.distance));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifTransportAnimal_.prix));
            }
        }
        return specification;
    }
}
