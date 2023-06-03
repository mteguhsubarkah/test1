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

import com.mycompany.myapp.domain.Anomaly;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AnomalyRepository;
import com.mycompany.myapp.service.dto.AnomalyCriteria;
import com.mycompany.myapp.service.dto.AnomalyDTO;
import com.mycompany.myapp.service.mapper.AnomalyMapper;

/**
 * Service for executing complex queries for {@link Anomaly} entities in the database.
 * The main input is a {@link AnomalyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnomalyDTO} or a {@link Page} of {@link AnomalyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnomalyQueryService extends QueryService<Anomaly> {

    private final Logger log = LoggerFactory.getLogger(AnomalyQueryService.class);

    private final AnomalyRepository anomalyRepository;

    private final AnomalyMapper anomalyMapper;

    public AnomalyQueryService(AnomalyRepository anomalyRepository, AnomalyMapper anomalyMapper) {
        this.anomalyRepository = anomalyRepository;
        this.anomalyMapper = anomalyMapper;
    }

    /**
     * Return a {@link List} of {@link AnomalyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnomalyDTO> findByCriteria(AnomalyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Anomaly> specification = createSpecification(criteria);
        return anomalyMapper.toDto(anomalyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnomalyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnomalyDTO> findByCriteria(AnomalyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Anomaly> specification = createSpecification(criteria);
        return anomalyRepository.findAll(specification, page)
            .map(anomalyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnomalyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Anomaly> specification = createSpecification(criteria);
        return anomalyRepository.count(specification);
    }

    /**
     * Function to convert {@link AnomalyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Anomaly> createSpecification(AnomalyCriteria criteria) {
        Specification<Anomaly> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Anomaly_.id));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Anomaly_.timestamp));
            }
            if (criteria.getMachine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMachine(), Anomaly_.machine));
            }
            if (criteria.getSensor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSensor(), Anomaly_.sensor));
            }
            if (criteria.getAnomaly() != null) {
                specification = specification.and(buildSpecification(criteria.getAnomaly(), Anomaly_.anomaly));
            }
            if (criteria.getSoundclip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoundclip(), Anomaly_.soundclip));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Anomaly_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Anomaly_.updatedAt));
            }
        }
        return specification;
    }
}
