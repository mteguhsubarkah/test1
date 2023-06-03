package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import javax.persistence.Lob;
import com.mycompany.myapp.domain.enumeration.ActionType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AnomalyItem} entity.
 */
public class AnomalyItemDTO implements Serializable {
    
    private Long id;

    private String reason;

    private String machine;

    private String coment;

    private ActionType action;

    private String soundclip;

    @Lob
    private byte[] soundData;

    private String soundDataContentType;
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;


    private Long anomalyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getSoundclip() {
        return soundclip;
    }

    public void setSoundclip(String soundclip) {
        this.soundclip = soundclip;
    }

    public byte[] getSoundData() {
        return soundData;
    }

    public void setSoundData(byte[] soundData) {
        this.soundData = soundData;
    }

    public String getSoundDataContentType() {
        return soundDataContentType;
    }

    public void setSoundDataContentType(String soundDataContentType) {
        this.soundDataContentType = soundDataContentType;
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

    public Long getAnomalyId() {
        return anomalyId;
    }

    public void setAnomalyId(Long anomalyId) {
        this.anomalyId = anomalyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnomalyItemDTO)) {
            return false;
        }

        return id != null && id.equals(((AnomalyItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnomalyItemDTO{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", machine='" + getMachine() + "'" +
            ", coment='" + getComent() + "'" +
            ", action='" + getAction() + "'" +
            ", soundclip='" + getSoundclip() + "'" +
            ", soundData='" + getSoundData() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", anomalyId=" + getAnomalyId() +
            "}";
    }
}
