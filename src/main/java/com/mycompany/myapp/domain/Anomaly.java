package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.mycompany.myapp.domain.enumeration.AnomalyType;

/**
 * A Anomaly.
 */
@Entity
@Table(name = "anomaly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Anomaly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @Column(name = "machine")
    private String machine;

    @Column(name = "sensor")
    private String sensor;

    @Enumerated(EnumType.STRING)
    @Column(name = "anomaly")
    private AnomalyType anomaly;

    @Column(name = "soundclip")
    private String soundclip;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Anomaly timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMachine() {
        return machine;
    }

    public Anomaly machine(String machine) {
        this.machine = machine;
        return this;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getSensor() {
        return sensor;
    }

    public Anomaly sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public AnomalyType getAnomaly() {
        return anomaly;
    }

    public Anomaly anomaly(AnomalyType anomaly) {
        this.anomaly = anomaly;
        return this;
    }

    public void setAnomaly(AnomalyType anomaly) {
        this.anomaly = anomaly;
    }

    public String getSoundclip() {
        return soundclip;
    }

    public Anomaly soundclip(String soundclip) {
        this.soundclip = soundclip;
        return this;
    }

    public void setSoundclip(String soundclip) {
        this.soundclip = soundclip;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Anomaly createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Anomaly updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anomaly)) {
            return false;
        }
        return id != null && id.equals(((Anomaly) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Anomaly{" +
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
