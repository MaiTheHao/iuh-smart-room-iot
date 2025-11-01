package com.example.smartroom.device_management.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.annotation.Filterable;
import com.example.smartroom.common.annotation.Searchable;
import com.example.smartroom.common.annotation.Sortable;
import com.example.smartroom.common.converter.ComponentStatusConverter;
import com.example.smartroom.common.enumeration.ComponentStatus;
import com.example.smartroom.data_ingestion.entity.SensorData;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity đại diện cho cảm biến bên trong một thiết bị.
 * Một cảm biến có thể đo nhiều loại dữ liệu.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "sensor")
public class Sensor extends AbstractAuditableEntity {
    /**
     * Khóa chính của cảm biến.
     */
    @Id
    @Sortable
    @Searchable
    private String id;

    /**
     * Tên cảm biến.
     */
    @Sortable
    @Searchable
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của cảm biến theo device.
     */
    @Sortable
    @Searchable
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái cảm biến.
     */
    @Sortable
    @Filterable
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả cảm biến.
     */
    @Column(name = "description")
    private String description;

    /**
     * Thiết bị chứa cảm biến này. Quan hệ ManyToOne với Device.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    /**
     * Các loại dữ liệu mà cảm biến này có thể đo. Quan hệ OneToMany với SensorDataType.
     */
    @OneToMany(
        mappedBy= "sensor",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<SensorDataType> sensorDataTypes = new HashSet<>();
    
    /**
     * Các dữ liệu mà cảm biến này thu thập được. Quan hệ OneToMany với SensorData.
     */
    @OneToMany(
        mappedBy = "sensor",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY 
    )
    private Set<SensorData> sensorDatas = new HashSet<>();
}
