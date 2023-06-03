package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.AnomalyItem;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AnomalyItemRepository;
import com.mycompany.myapp.service.dto.AnomalyItemCriteria;
import com.mycompany.myapp.service.dto.AnomalyItemDTO;
import com.mycompany.myapp.service.mapper.AnomalyItemMapper;

/**
 * Service for executing complex queries for {@link AnomalyItem} entities in the database.
 * The main input is a {@link AnomalyItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnomalyItemDTO} or a {@link Page} of {@link AnomalyItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnomalyItemQueryService extends QueryService<AnomalyItem> {

    private final Logger log = LoggerFactory.getLogger(AnomalyItemQueryService.class);

    private final AnomalyItemRepository anomalyItemRepository;

    private final AnomalyItemMapper anomalyItemMapper;

    public AnomalyItemQueryService(AnomalyItemRepository anomalyItemRepository, AnomalyItemMapper anomalyItemMapper) {
        this.anomalyItemRepository = anomalyItemRepository;
        this.anomalyItemMapper = anomalyItemMapper;
    }

    /**
     * Return a {@link List} of {@link AnomalyItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnomalyItemDTO> findByCriteria(AnomalyItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnomalyItem> specification = createSpecification(criteria);
        return anomalyItemMapper.toDto(anomalyItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnomalyItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnomalyItemDTO> findByCriteria(AnomalyItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnomalyItem> specification = createSpecification(criteria);
        return anomalyItemRepository.findAll(specification, page)
            .map(anomalyItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnomalyItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnomalyItem> specification = createSpecification(criteria);
        return anomalyItemRepository.count(specification);
    }

    /**
     * Function to convert {@link AnomalyItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnomalyItem> createSpecification(AnomalyItemCriteria criteria) {
        Specification<AnomalyItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnomalyItem_.id));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), AnomalyItem_.reason));
            }
            if (criteria.getMachine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMachine(), AnomalyItem_.machine));
            }
            if (criteria.getComent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComent(), AnomalyItem_.coment));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildSpecification(criteria.getAction(), AnomalyItem_.action));
            }
            if (criteria.getSoundclip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoundclip(), AnomalyItem_.soundclip));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), AnomalyItem_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), AnomalyItem_.updatedAt));
            }
            if (criteria.getAnomalyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnomalyId(),
                    root -> root.join(AnomalyItem_.anomaly, JoinType.LEFT).get(Anomaly_.id)));
            }
        }
        return specification;
    }
}
