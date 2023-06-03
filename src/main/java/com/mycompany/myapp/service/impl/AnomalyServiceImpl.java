package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AnomalyService;
import com.mycompany.myapp.domain.Anomaly;
import com.mycompany.myapp.repository.AnomalyRepository;
import com.mycompany.myapp.service.dto.AnomalyDTO;
import com.mycompany.myapp.service.mapper.AnomalyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Anomaly}.
 */
@Service
@Transactional
public class AnomalyServiceImpl implements AnomalyService {

    private final Logger log = LoggerFactory.getLogger(AnomalyServiceImpl.class);

    private final AnomalyRepository anomalyRepository;

    private final AnomalyMapper anomalyMapper;

    public AnomalyServiceImpl(AnomalyRepository anomalyRepository, AnomalyMapper anomalyMapper) {
        this.anomalyRepository = anomalyRepository;
        this.anomalyMapper = anomalyMapper;
    }

    @Override
    public AnomalyDTO save(AnomalyDTO anomalyDTO) {
        log.debug("Request to save Anomaly : {}", anomalyDTO);
        Anomaly anomaly = anomalyMapper.toEntity(anomalyDTO);
        anomaly = anomalyRepository.save(anomaly);
        return anomalyMapper.toDto(anomaly);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnomalyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anomalies");
        return anomalyRepository.findAll(pageable)
            .map(anomalyMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AnomalyDTO> findOne(Long id) {
        log.debug("Request to get Anomaly : {}", id);
        return anomalyRepository.findById(id)
            .map(anomalyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anomaly : {}", id);
        anomalyRepository.deleteById(id);
    }
}
