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

import com.pfe.covite.domain.Livreur;
import com.pfe.covite.domain.*; // for static metamodels
import com.pfe.covite.repository.LivreurRepository;
import com.pfe.covite.service.dto.LivreurCriteria;

/**
 * Service for executing complex queries for {@link Livreur} entities in the database.
 * The main input is a {@link LivreurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Livreur} or a {@link Page} of {@link Livreur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LivreurQueryService extends QueryService<Livreur> {

    private final Logger log = LoggerFactory.getLogger(LivreurQueryService.class);

    private final LivreurRepository livreurRepository;

    public LivreurQueryService(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    /**
     * Return a {@link List} of {@link Livreur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Livreur> findByCriteria(LivreurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Livreur> specification = createSpecification(criteria);
        return livreurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Livreur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Livreur> findByCriteria(LivreurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Livreur> specification = createSpecification(criteria);
        return livreurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LivreurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Livreur> specification = createSpecification(criteria);
        return livreurRepository.count(specification);
    }

    /**
     * Function to convert {@link LivreurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Livreur> createSpecification(LivreurCriteria criteria) {
        Specification<Livreur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Livreur_.id));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Livreur_.telephone));
            }
            if (criteria.getSolde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolde(), Livreur_.solde));
            }
            if (criteria.getCin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCin(), Livreur_.cin));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Livreur_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getVehiculeId() != null) {
                specification = specification.and(buildSpecification(criteria.getVehiculeId(),
                    root -> root.join(Livreur_.vehicule, JoinType.LEFT).get(Vehicule_.id)));
            }
        }
        return specification;
    }
}
