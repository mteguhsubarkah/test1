package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnomalyItemMapperTest {

    private AnomalyItemMapper anomalyItemMapper;

    @BeforeEach
    public void setUp() {
        anomalyItemMapper = new AnomalyItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(anomalyItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(anomalyItemMapper.fromId(null)).isNull();
    }
}
