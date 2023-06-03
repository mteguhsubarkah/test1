package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnomalyItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnomalyItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnomalyItemRepository extends JpaRepository<AnomalyItem, Long>, JpaSpecificationExecutor<AnomalyItem> {
}
