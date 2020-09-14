package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeLivraisonAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CommandeLivraisonAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLivraisonAnimalRepository extends JpaRepository<CommandeLivraisonAnimal, Long> {

    @Query("select commandeLivraisonAnimal from CommandeLivraisonAnimal commandeLivraisonAnimal where commandeLivraisonAnimal.client.login = ?#{principal.username}")
    List<CommandeLivraisonAnimal> findByClientIsCurrentUser();

    @Query("select commandeLivraisonAnimal from CommandeLivraisonAnimal commandeLivraisonAnimal where commandeLivraisonAnimal.livreur.login = ?#{principal.username}")
    List<CommandeLivraisonAnimal> findByLivreurIsCurrentUser();
}
