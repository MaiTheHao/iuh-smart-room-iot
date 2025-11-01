package com.example.smartroom.device_management.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.annotation.Filterable;
import com.example.smartroom.common.annotation.Searchable;
import com.example.smartroom.common.annotation.Sortable;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity đại diện cho một thiết bị thông minh được quản lý bởi một hub.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "device")
public class Device extends AbstractAuditableEntity{
    /**
     * Khóa chính của thiết bị.
     */
    @Id
    @Sortable
    @Searchable
    private String id;

    /**
     * Tên thiết bị.
     */
    @Sortable
    @Searchable
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của thiết bị trong phòng so với hub.
     */
    @Sortable
    @Searchable
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái thiết bị.
     */
    @Sortable
    @Filterable
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả thiết bị.
     */
    @Column(name = "description")
    private String description;

    /**
     * Loại giao thức kết nối của thiết bị. Quan hệ ManyToOne với ConnectionType.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_type_id")
    private ConnectionType connectionType;

    /**
     * Hub quản lý thiết bị này. Quan hệ ManyToOne với Hub.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;
    
    /**
     * Danh sách các cảm biến được quản lý bởi thiết bị này. Quan hệ OneToMany với Sensor.
     */
    @OneToMany(
        mappedBy = "device",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    public Set<Sensor> sensors = new HashSet<>();
}
