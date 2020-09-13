package com.pfe.covite.repository;

import com.pfe.covite.domain.Livreur;
import com.pfe.covite.domain.Notification;

import com.pfe.covite.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
