package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AnomalyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anomaly} and its DTO {@link AnomalyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnomalyMapper extends EntityMapper<AnomalyDTO, Anomaly> {



    default Anomaly fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anomaly anomaly = new Anomaly();
        anomaly.setId(id);
        return anomaly;
    }
}
