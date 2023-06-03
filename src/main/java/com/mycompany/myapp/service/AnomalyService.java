package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AnomalyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Anomaly}.
 */
public interface AnomalyService {

    /**
     * Save a anomaly.
     *
     * @param anomalyDTO the entity to save.
     * @return the persisted entity.
     */
    AnomalyDTO save(AnomalyDTO anomalyDTO);

    /**
     * Get all the anomalies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnomalyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" anomaly.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnomalyDTO> findOne(Long id);

    /**
     * Delete the "id" anomaly.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
