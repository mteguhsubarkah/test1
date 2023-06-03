package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.AnomalyType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Anomaly} entity.
 */
public class AnomalyDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime timestamp;

    private String machine;

    private String sensor;

    private AnomalyType anomaly;

    private String soundclip;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public AnomalyType getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(AnomalyType anomaly) {
        this.anomaly = anomaly;
    }

    public String getSoundclip() {
        return soundclip;
    }

    public void setSoundclip(String soundclip) {
        this.soundclip = soundclip;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnomalyDTO)) {
            return false;
        }

        return id != null && id.equals(((AnomalyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnomalyDTO{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", machine='" + getMachine() + "'" +
            ", sensor='" + getSensor() + "'" +
            ", anomaly='" + getAnomaly() + "'" +
            ", soundclip='" + getSoundclip() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
