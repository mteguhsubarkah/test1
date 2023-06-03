package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AnomalyItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnomalyItem.class);
        AnomalyItem anomalyItem1 = new AnomalyItem();
        anomalyItem1.setId(1L);
        AnomalyItem anomalyItem2 = new AnomalyItem();
        anomalyItem2.setId(anomalyItem1.getId());
        assertThat(anomalyItem1).isEqualTo(anomalyItem2);
        anomalyItem2.setId(2L);
        assertThat(anomalyItem1).isNotEqualTo(anomalyItem2);
        anomalyItem1.setId(null);
        assertThat(anomalyItem1).isNotEqualTo(anomalyItem2);
    }
}
