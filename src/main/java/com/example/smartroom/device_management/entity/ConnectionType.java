package com.example.smartroom.device_management.entity;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity định nghĩa loại giao thức kết nối của thiết bị (ví dụ: Wi-Fi, Zigbee).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Long id;

    /**
     * Mã loại kết nối (unique).
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Tên loại kết nối.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Mô tả loại kết nối.
     */
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionType that = (ConnectionType) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
