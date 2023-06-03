package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AnomalyItemService;
import com.mycompany.myapp.domain.AnomalyItem;
import com.mycompany.myapp.repository.AnomalyItemRepository;
import com.mycompany.myapp.service.dto.AnomalyItemDTO;
import com.mycompany.myapp.service.mapper.AnomalyItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnomalyItem}.
 */
@Service
@Transactional
public class AnomalyItemServiceImpl implements AnomalyItemService {

    private final Logger log = LoggerFactory.getLogger(AnomalyItemServiceImpl.class);

    private final AnomalyItemRepository anomalyItemRepository;

    private final AnomalyItemMapper anomalyItemMapper;

    public AnomalyItemServiceImpl(AnomalyItemRepository anomalyItemRepository, AnomalyItemMapper anomalyItemMapper) {
        this.anomalyItemRepository = anomalyItemRepository;
        this.anomalyItemMapper = anomalyItemMapper;
    }

    @Override
    public AnomalyItemDTO save(AnomalyItemDTO anomalyItemDTO) {
        log.debug("Request to save AnomalyItem : {}", anomalyItemDTO);
        AnomalyItem anomalyItem = anomalyItemMapper.toEntity(anomalyItemDTO);
        anomalyItem = anomalyItemRepository.save(anomalyItem);
        return anomalyItemMapper.toDto(anomalyItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnomalyItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnomalyItems");
        return anomalyItemRepository.findAll(pageable)
            .map(anomalyItemMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AnomalyItemDTO> findOne(Long id) {
        log.debug("Request to get AnomalyItem : {}", id);
        return anomalyItemRepository.findById(id)
            .map(anomalyItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnomalyItem : {}", id);
        anomalyItemRepository.deleteById(id);
    }
}
