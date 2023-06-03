package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.AnomalyType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Anomaly} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AnomalyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anomalies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnomalyCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AnomalyType
     */
    public static class AnomalyTypeFilter extends Filter<AnomalyType> {

        public AnomalyTypeFilter() {
        }

        public AnomalyTypeFilter(AnomalyTypeFilter filter) {
            super(filter);
        }

        @Override
        public AnomalyTypeFilter copy() {
            return new AnomalyTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter timestamp;

    private StringFilter machine;

    private StringFilter sensor;

    private AnomalyTypeFilter anomaly;

    private StringFilter soundclip;

    private ZonedDateTimeFilter createdAt;

    private ZonedDateTimeFilter updatedAt;

    public AnomalyCriteria() {
    }

    public AnomalyCriteria(AnomalyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.machine = other.machine == null ? null : other.machine.copy();
        this.sensor = other.sensor == null ? null : other.sensor.copy();
        this.anomaly = other.anomaly == null ? null : other.anomaly.copy();
        this.soundclip = other.soundclip == null ? null : other.soundclip.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
    }

    @Override
    public AnomalyCriteria copy() {
        return new AnomalyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTimeFilter timestamp) {
        this.timestamp = timestamp;
    }

    public StringFilter getMachine() {
        return machine;
    }

    public void setMachine(StringFilter machine) {
        this.machine = machine;
    }

    public StringFilter getSensor() {
        return sensor;
    }

    public void setSensor(StringFilter sensor) {
        this.sensor = sensor;
    }

    public AnomalyTypeFilter getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(AnomalyTypeFilter anomaly) {
        this.anomaly = anomaly;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnomalyCriteria that = (AnomalyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(machine, that.machine) &&
            Objects.equals(sensor, that.sensor) &&
            Objects.equals(anomaly, that.anomaly) &&
            Objects.equals(soundclip, that.soundclip) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timestamp,
        machine,
        sensor,
        anomaly,
        soundclip,
        createdAt,
        updatedAt
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnomalyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
                (machine != null ? "machine=" + machine + ", " : "") +
                (sensor != null ? "sensor=" + sensor + ", " : "") +
                (anomaly != null ? "anomaly=" + anomaly + ", " : "") +
                (soundclip != null ? "soundclip=" + soundclip + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            "}";
    }

}
