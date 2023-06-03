package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AnomalyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anomaly.class);
        Anomaly anomaly1 = new Anomaly();
        anomaly1.setId(1L);
        Anomaly anomaly2 = new Anomaly();
        anomaly2.setId(anomaly1.getId());
        assertThat(anomaly1).isEqualTo(anomaly2);
        anomaly2.setId(2L);
        assertThat(anomaly1).isNotEqualTo(anomaly2);
        anomaly1.setId(null);
        assertThat(anomaly1).isNotEqualTo(anomaly2);
    }
}
