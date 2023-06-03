package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.ActionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.AnomalyItem} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AnomalyItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anomaly-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnomalyItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ActionType
     */
    public static class ActionTypeFilter extends Filter<ActionType> {

        public ActionTypeFilter() {
        }

        public ActionTypeFilter(ActionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ActionTypeFilter copy() {
            return new ActionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reason;

    private StringFilter machine;

    private StringFilter coment;

    private ActionTypeFilter action;

    private StringFilter soundclip;

    private ZonedDateTimeFilter createdAt;

    private ZonedDateTimeFilter updatedAt;

    private LongFilter anomalyId;

    public AnomalyItemCriteria() {
    }

    public AnomalyItemCriteria(AnomalyItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.machine = other.machine == null ? null : other.machine.copy();
        this.coment = other.coment == null ? null : other.coment.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.soundclip = other.soundclip == null ? null : other.soundclip.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.anomalyId = other.anomalyId == null ? null : other.anomalyId.copy();
    }

    @Override
    public AnomalyItemCriteria copy() {
        return new AnomalyItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public StringFilter getMachine() {
        return machine;
    }

    public void setMachine(StringFilter machine) {
        this.machine = machine;
    }

    public StringFilter getComent() {
        return coment;
    }

    public void setComent(StringFilter coment) {
        this.coment = coment;
    }

    public ActionTypeFilter getAction() {
        return action;
    }

    public void setAction(ActionTypeFilter action) {
        this.action = action;
    }

    public StringFilter getSoundclip() {
        return soundclip;
    }

    public void setSoundclip(StringFilter soundclip) {
        this.soundclip = soundclip;
    }

    public ZonedDateTimeFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTimeFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTimeFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getAnomalyId() {
        return anomalyId;
    }

    public void setAnomalyId(LongFilter anomalyId) {
        this.anomalyId = anomalyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnomalyItemCriteria that = (AnomalyItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(machine, that.machine) &&
            Objects.equals(coment, that.coment) &&
            Objects.equals(action, that.action) &&
            Objects.equals(soundclip, that.soundclip) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(anomalyId, that.anomalyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reason,
        machine,
        coment,
        action,
        soundclip,
        createdAt,
        updatedAt,
        anomalyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnomalyItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (machine != null ? "machine=" + machine + ", " : "") +
                (coment != null ? "coment=" + coment + ", " : "") +
                (action != null ? "action=" + action + ", " : "") +
                (soundclip != null ? "soundclip=" + soundclip + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (anomalyId != null ? "anomalyId=" + anomalyId + ", " : "") +
            "}";
    }

}
