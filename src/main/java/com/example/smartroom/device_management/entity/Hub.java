package com.example.smartroom.device_management.entity;

import java.util.HashSet;
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
 * Entity đại diện cho bộ điều khiển trung tâm (hub) trong một phòng.
 * Một hub quản lý nhiều thiết bị và thuộc về một phòng.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hub")
public class Hub extends AbstractAuditableEntity {
    /**
     * Khóa chính của hub.
     */
    @Id
    private String id;

    /**
     * Tên hub.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của hub trong phòng (ví dụ: "Góc phòng", "Trên bàn").
     */
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái của hub.
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả hub.
     */
    @Column(name = "description")
    private String description;

    /**
     * Phòng chứa hub này.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * Danh sách thiết bị được quản lý bởi hub này.
     */
    @OneToMany(
        mappedBy = "hub",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Device> devices = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Hub hub = (Hub) o;

        return id != null && id.equals(hub.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
