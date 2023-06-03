package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Anomaly;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Anomaly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnomalyRepository extends JpaRepository<Anomaly, Long>, JpaSpecificationExecutor<Anomaly> {
}
