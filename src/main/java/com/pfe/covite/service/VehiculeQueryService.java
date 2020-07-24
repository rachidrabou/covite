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

import com.pfe.covite.domain.Vehicule;
import com.pfe.covite.domain.*; // for static metamodels
import com.pfe.covite.repository.VehiculeRepository;
import com.pfe.covite.service.dto.VehiculeCriteria;

/**
 * Service for executing complex queries for {@link Vehicule} entities in the database.
 * The main input is a {@link VehiculeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Vehicule} or a {@link Page} of {@link Vehicule} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehiculeQueryService extends QueryService<Vehicule> {

    private final Logger log = LoggerFactory.getLogger(VehiculeQueryService.class);

    private final VehiculeRepository vehiculeRepository;

    public VehiculeQueryService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Return a {@link List} of {@link Vehicule} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Vehicule> findByCriteria(VehiculeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vehicule> specification = createSpecification(criteria);
        return vehiculeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Vehicule} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Vehicule> findByCriteria(VehiculeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vehicule> specification = createSpecification(criteria);
        return vehiculeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehiculeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vehicule> specification = createSpecification(criteria);
        return vehiculeRepository.count(specification);
    }

    /**
     * Function to convert {@link VehiculeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vehicule> createSpecification(VehiculeCriteria criteria) {
        Specification<Vehicule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vehicule_.id));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Vehicule_.matricule));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Vehicule_.type));
            }
            if (criteria.getCapacite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapacite(), Vehicule_.capacite));
            }
        }
        return specification;
    }
}
