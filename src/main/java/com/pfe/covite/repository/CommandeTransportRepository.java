package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeTransport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CommandeTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeTransportRepository extends JpaRepository<CommandeTransport, Long> {

    @Query("select commandeTransport from CommandeTransport commandeTransport where commandeTransport.client.login = ?#{principal.username}")
    List<CommandeTransport> findByClientIsCurrentUser();

    @Query("select commandeTransport from CommandeTransport commandeTransport where commandeTransport.livreur.login = ?#{principal.username}")
    List<CommandeTransport> findByLivreurIsCurrentUser();
}
