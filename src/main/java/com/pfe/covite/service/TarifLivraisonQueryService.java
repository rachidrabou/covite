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

import com.pfe.covite.domain.TarifLivraison;
import com.pfe.covite.domain.*; // for static metamodels
import com.pfe.covite.repository.TarifLivraisonRepository;
import com.pfe.covite.service.dto.TarifLivraisonCriteria;

/**
 * Service for executing complex queries for {@link TarifLivraison} entities in the database.
 * The main input is a {@link TarifLivraisonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifLivraison} or a {@link Page} of {@link TarifLivraison} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifLivraisonQueryService extends QueryService<TarifLivraison> {

    private final Logger log = LoggerFactory.getLogger(TarifLivraisonQueryService.class);

    private final TarifLivraisonRepository tarifLivraisonRepository;

    public TarifLivraisonQueryService(TarifLivraisonRepository tarifLivraisonRepository) {
        this.tarifLivraisonRepository = tarifLivraisonRepository;
    }

    /**
     * Return a {@link List} of {@link TarifLivraison} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifLivraison> findByCriteria(TarifLivraisonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifLivraison> specification = createSpecification(criteria);
        return tarifLivraisonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifLivraison} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifLivraison> findByCriteria(TarifLivraisonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifLivraison> specification = createSpecification(criteria);
        return tarifLivraisonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifLivraisonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifLivraison> specification = createSpecification(criteria);
        return tarifLivraisonRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifLivraisonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifLivraison> createSpecification(TarifLivraisonCriteria criteria) {
        Specification<TarifLivraison> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifLivraison_.id));
            }
            if (criteria.getService() != null) {
                specification = specification.and(buildStringSpecification(criteria.getService(), TarifLivraison_.service));
            }
            if (criteria.getObjet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObjet(), TarifLivraison_.objet));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistance(), TarifLivraison_.distance));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifLivraison_.prix));
            }
        }
        return specification;
    }
}
