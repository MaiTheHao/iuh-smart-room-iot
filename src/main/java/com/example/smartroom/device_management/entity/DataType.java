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
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity định nghĩa loại dữ liệu đo được bởi cảm biến (ví dụ: nhiệt độ, độ ẩm).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "data_type",
    indexes = {
        @Index(name = "idx_data_type_code", columnList = "code", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_data_type_code", columnNames = {"code"})
    }
)
public class DataType extends AbstractAuditableEntity {
    /**
     * Khóa chính của loại dữ liệu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Mã loại dữ liệu (unique).
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Tên loại dữ liệu.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Mô tả loại dữ liệu.
     */
    @Column(name = "description")
    private String description;

    /**
     * Đơn vị đo (ví dụ: °C, %, ppm).
     */
    @Column(name = "unit", nullable = false)
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        DataType dataType = (DataType) o;

        return code != null && code.equals(dataType.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
