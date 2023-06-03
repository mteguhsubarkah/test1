package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnomalyMapperTest {

    private AnomalyMapper anomalyMapper;

    @BeforeEach
    public void setUp() {
        anomalyMapper = new AnomalyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(anomalyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(anomalyMapper.fromId(null)).isNull();
    }
}
