package com.pfe.covite.repository;

import com.pfe.covite.domain.Vehicule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long>, JpaSpecificationExecutor<Vehicule> {
}
