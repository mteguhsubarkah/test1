package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.mycompany.myapp.domain.enumeration.ActionType;

/**
 * A AnomalyItem.
 */
@Entity
@Table(name = "anomaly_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnomalyItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "machine")
    private String machine;

    @Column(name = "coment")
    private String coment;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private ActionType action;

    @Column(name = "soundclip")
    private String soundclip;

    @Lob
    @Column(name = "sound_data")
    private byte[] soundData;

    @Column(name = "sound_data_content_type")
    private String soundDataContentType;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "anomalyItems", allowSetters = true)
    private Anomaly anomaly;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public AnomalyItem reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMachine() {
        return machine;
    }

    public AnomalyItem machine(String machine) {
        this.machine = machine;
        return this;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getComent() {
        return coment;
    }

    public AnomalyItem coment(String coment) {
        this.coment = coment;
        return this;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public ActionType getAction() {
        return action;
    }

    public AnomalyItem action(ActionType action) {
        this.action = action;
        return this;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getSoundclip() {
        return soundclip;
    }

    public AnomalyItem soundclip(String soundclip) {
        this.soundclip = soundclip;
        return this;
    }

    public void setSoundclip(String soundclip) {
        this.soundclip = soundclip;
    }

    public byte[] getSoundData() {
        return soundData;
    }

    public AnomalyItem soundData(byte[] soundData) {
        this.soundData = soundData;
        return this;
    }

    public void setSoundData(byte[] soundData) {
        this.soundData = soundData;
    }

    public String getSoundDataContentType() {
        return soundDataContentType;
    }

    public AnomalyItem soundDataContentType(String soundDataContentType) {
        this.soundDataContentType = soundDataContentType;
        return this;
    }

    public void setSoundDataContentType(String soundDataContentType) {
        this.soundDataContentType = soundDataContentType;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public AnomalyItem createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public AnomalyItem updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Anomaly getAnomaly() {
        return anomaly;
    }

    public AnomalyItem anomaly(Anomaly anomaly) {
        this.anomaly = anomaly;
        return this;
    }

    public void setAnomaly(Anomaly anomaly) {
        this.anomaly = anomaly;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnomalyItem)) {
            return false;
        }
        return id != null && id.equals(((AnomalyItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnomalyItem{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", machine='" + getMachine() + "'" +
            ", coment='" + getComent() + "'" +
            ", action='" + getAction() + "'" +
            ", soundclip='" + getSoundclip() + "'" +
            ", soundData='" + getSoundData() + "'" +
            ", soundDataContentType='" + getSoundDataContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
