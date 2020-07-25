package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeLivraison;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommandeLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLivraisonRepository extends JpaRepository<CommandeLivraison, Long> {
}
