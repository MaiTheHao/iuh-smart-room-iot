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
 * Entity đại diện cho bộ điều khiển trung tâm (hub) trong một phòng.
 * Một hub quản lý nhiều thiết bị và thuộc về một phòng.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "hub")
public class Hub extends AbstractAuditableEntity {
    /**
     * Khóa chính của hub.
     */
    @Id
    @Sortable
    @Searchable
    private String id;

    /**
     * Tên hub.
     */
    @Sortable
    @Searchable
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Vị trí của hub trong phòng (ví dụ: "Góc phòng", "Trên bàn").
     */
    @Sortable
    @Searchable
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * Trạng thái của hub.
     */
    @Sortable
    @Filterable
    @Column(name = "status", nullable = false)
    @Convert(converter = ComponentStatusConverter.class)
    private ComponentStatus status;

    /**
     * Mô tả hub.
     */
    @Column(name = "description")
    private String description;

    /**
     * Phòng chứa hub này. Quan hệ ManyToOne với Room.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * Danh sách thiết bị được quản lý bởi hub này. Quan hệ OneToMany với Device.
     */
    @OneToMany(
        mappedBy = "hub",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Device> devices = new HashSet<>();
}
