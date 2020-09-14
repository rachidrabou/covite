package com.pfe.covite.repository;

import com.pfe.covite.domain.Livreur;

import com.pfe.covite.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Livreur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long>, JpaSpecificationExecutor<Livreur> {
    Livreur findByUser(User user);
}
