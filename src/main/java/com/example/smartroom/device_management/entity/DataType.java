package com.example.smartroom.device_management.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;

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
 * Entity định nghĩa loại dữ liệu đo được bởi cảm biến (ví dụ: nhiệt độ, độ ẩm).
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "code", callSuper = false)
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

    /**
     * Các cảm biến có thể đo loại dữ liệu này.
     */
    @OneToMany(
        mappedBy = "dataType",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<SensorDataType> sensorDataTypes = new HashSet<>();
}
