package com.pfe.covite.repository;

import com.pfe.covite.domain.TarifLivraison;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifLivraisonRepository extends JpaRepository<TarifLivraison, Long>, JpaSpecificationExecutor<TarifLivraison> {
}
