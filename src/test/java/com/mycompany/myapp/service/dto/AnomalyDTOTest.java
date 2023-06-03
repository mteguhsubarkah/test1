package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AnomalyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnomalyDTO.class);
        AnomalyDTO anomalyDTO1 = new AnomalyDTO();
        anomalyDTO1.setId(1L);
        AnomalyDTO anomalyDTO2 = new AnomalyDTO();
        assertThat(anomalyDTO1).isNotEqualTo(anomalyDTO2);
        anomalyDTO2.setId(anomalyDTO1.getId());
        assertThat(anomalyDTO1).isEqualTo(anomalyDTO2);
        anomalyDTO2.setId(2L);
        assertThat(anomalyDTO1).isNotEqualTo(anomalyDTO2);
        anomalyDTO1.setId(null);
        assertThat(anomalyDTO1).isNotEqualTo(anomalyDTO2);
    }
}
