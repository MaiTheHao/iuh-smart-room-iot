package com.example.smartroom.device_management.entity;

import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.annotation.Searchable;
import com.example.smartroom.common.annotation.Sortable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity định nghĩa loại giao thức kết nối của thiết bị (ví dụ: Wi-Fi, Zigbee).
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(
    name = "connection_type",
    indexes = {
        @Index(name = "idx_connection_type_code", columnList = "code", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_connection_type_code", columnNames = {"code"})
    }
)
public class ConnectionType extends AbstractAuditableEntity {
    /**
     * Khóa chính của loại kết nối.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Sortable
    private Long id;

    /**
     * Mã loại kết nối (unique).
     */
    @Sortable
    @Searchable
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Tên loại kết nối.
     */
    @Sortable
    @Searchable
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Mô tả loại kết nối.
     */
    @Column(name = "description")
    private String description;

    /**
     * Tập hợp các thiết bị sử dụng loại kết nối này.
     */
    @OneToMany(
        mappedBy = "connectionType",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Device> devices;
}
