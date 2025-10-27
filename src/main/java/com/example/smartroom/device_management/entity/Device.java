package com.example.smartroom.device_management.entity;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.converter.ComponentStatusConverter;
import com.example.smartroom.common.enumeration.ComponentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity đại diện cho một thiết bị thông minh được quản lý bởi một hub.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device")
public class Device extends AbstractAuditableEntity{
    /**
     * Khóa chính của thiết bị.
     */
    @Id
    private String id;

    /**
     * Tên thiết bị.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của thiết bị trong phòng so với hub.
     */
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái thiết bị.
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả thiết bị.
     */
    @Column(name = "description")
    private String description;

    /**
     * Hub quản lý thiết bị này.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return id != null ? id.equals(device.id) : device.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
