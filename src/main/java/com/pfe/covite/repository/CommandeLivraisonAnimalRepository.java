package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeLivraisonAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommandeLivraisonAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLivraisonAnimalRepository extends JpaRepository<CommandeLivraisonAnimal, Long> {
}
