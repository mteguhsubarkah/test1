package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AnomalyItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.AnomalyItem}.
 */
public interface AnomalyItemService {

    /**
     * Save a anomalyItem.
     *
     * @param anomalyItemDTO the entity to save.
     * @return the persisted entity.
     */
    AnomalyItemDTO save(AnomalyItemDTO anomalyItemDTO);

    /**
     * Get all the anomalyItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnomalyItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" anomalyItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnomalyItemDTO> findOne(Long id);

    /**
     * Delete the "id" anomalyItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
