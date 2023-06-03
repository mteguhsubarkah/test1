package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AnomalyItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnomalyItemDTO.class);
        AnomalyItemDTO anomalyItemDTO1 = new AnomalyItemDTO();
        anomalyItemDTO1.setId(1L);
        AnomalyItemDTO anomalyItemDTO2 = new AnomalyItemDTO();
        assertThat(anomalyItemDTO1).isNotEqualTo(anomalyItemDTO2);
        anomalyItemDTO2.setId(anomalyItemDTO1.getId());
        assertThat(anomalyItemDTO1).isEqualTo(anomalyItemDTO2);
        anomalyItemDTO2.setId(2L);
        assertThat(anomalyItemDTO1).isNotEqualTo(anomalyItemDTO2);
        anomalyItemDTO1.setId(null);
        assertThat(anomalyItemDTO1).isNotEqualTo(anomalyItemDTO2);
    }
}
