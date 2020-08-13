package com.pfe.covite.repository;

import com.pfe.covite.domain.TarifTransportAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifTransportAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifTransportAnimalRepository extends JpaRepository<TarifTransportAnimal, Long>, JpaSpecificationExecutor<TarifTransportAnimal> {
}
