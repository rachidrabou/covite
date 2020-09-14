package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeLivraison;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CommandeLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLivraisonRepository extends JpaRepository<CommandeLivraison, Long> {

    @Query("select commandeLivraison from CommandeLivraison commandeLivraison where commandeLivraison.client.login = ?#{principal.username}")
    List<CommandeLivraison> findByClientIsCurrentUser();

    @Query("select commandeLivraison from CommandeLivraison commandeLivraison where commandeLivraison.livreur.login = ?#{principal.username}")
    List<CommandeLivraison> findByLivreurIsCurrentUser();
}
