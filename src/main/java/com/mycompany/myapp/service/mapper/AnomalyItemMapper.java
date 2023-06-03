package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AnomalyItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnomalyItem} and its DTO {@link AnomalyItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnomalyMapper.class})
public interface AnomalyItemMapper extends EntityMapper<AnomalyItemDTO, AnomalyItem> {

    @Mapping(source = "anomaly.id", target = "anomalyId")
    AnomalyItemDTO toDto(AnomalyItem anomalyItem);

    @Mapping(source = "anomalyId", target = "anomaly")
    AnomalyItem toEntity(AnomalyItemDTO anomalyItemDTO);

    default AnomalyItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnomalyItem anomalyItem = new AnomalyItem();
        anomalyItem.setId(id);
        return anomalyItem;
    }
}
