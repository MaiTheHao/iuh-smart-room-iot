package com.example.smartroom.device_management.entity;

import java.util.Objects;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.device_management.entity.id.SensorDataTypeId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity bảng nối giữa cảm biến và loại dữ liệu.
 * Xác định cảm biến có thể đo loại dữ liệu nào.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "sensor_data_type",
    indexes = {
        @Index(name = "idx_sensor_id_data_type_sensor_id", columnList = "sensor_id, data_type_id"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_sensor_id_data_type_sensor_id", columnNames = {"sensor_id", "data_type_id"})
    }
)
public class SensorDataType extends AbstractAuditableEntity {
    /**
     * Khóa chính tổng hợp gồm sensorId và dataTypeId.
     */
    @EmbeddedId
    private SensorDataTypeId id;

    /**
     * Cảm biến liên kết.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sensorId")
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    /**
     * Loại dữ liệu liên kết.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dataTypeId")
    @JoinColumn(name = "data_type_id")
    private DataType dataType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorDataType that = (SensorDataType) o;

        String thisSensorId = (this.sensor != null) ? this.sensor.getId() : null;
        String thatSensorId = (that.sensor != null) ? that.sensor.getId() : null;

        Long thisDataTypeId = (this.dataType != null) ? this.dataType.getId() : null;
        Long thatDataTypeId = (that.dataType != null) ? that.dataType.getId() : null;

        return Objects.equals(thisSensorId, thatSensorId) && Objects.equals(thisDataTypeId, thatDataTypeId);
    }

    @Override
    public int hashCode() {
        String thisSensorId = (this.sensor != null) ? this.sensor.getId() : null;
        Long thisDataTypeId = (this.dataType != null) ? this.dataType.getId() : null;
        return Objects.hash(thisSensorId, thisDataTypeId);
    }
}