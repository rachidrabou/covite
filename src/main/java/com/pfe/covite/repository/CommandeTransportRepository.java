package com.pfe.covite.repository;

import com.pfe.covite.domain.CommandeTransport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommandeTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeTransportRepository extends JpaRepository<CommandeTransport, Long> {
}
