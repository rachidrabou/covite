package com.pfe.covite.repository;

import com.pfe.covite.domain.Commandes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Commandes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandesRepository extends JpaRepository<Commandes, Long>, JpaSpecificationExecutor<Commandes> {
}
