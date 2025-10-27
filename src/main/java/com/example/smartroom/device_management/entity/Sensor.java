package com.example.smartroom.device_management.entity;

import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.converter.ComponentStatusConverter;
import com.example.smartroom.common.enumeration.ComponentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity đại diện cho cảm biến bên trong một thiết bị.
 * Một cảm biến có thể đo nhiều loại dữ liệu.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor")
public class Sensor extends AbstractAuditableEntity {
    /**
     * Khóa chính của cảm biến.
     */
    @Id
    private String id;

    /**
     * Tên cảm biến.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của cảm biến theo device.
     */
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái cảm biến.
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả cảm biến.
     */
    @Column(name = "description")
    private String description;

    /**
     * Thiết bị chứa cảm biến này.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    /**
     * Các loại dữ liệu mà cảm biến này có thể đo.
     */
    @OneToMany(
        mappedBy = "sensor",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<SensorDataType> sensorDataTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        
        Sensor sensor = (Sensor) o;

        return id != null && id.equals(sensor.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
