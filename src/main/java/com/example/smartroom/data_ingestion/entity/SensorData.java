package com.example.smartroom.data_ingestion.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.smartroom.device_management.entity.DataType;
import com.example.smartroom.device_management.entity.Sensor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity đại diện cho dữ liệu được thu thập từ một {@link Sensor} cụ thể.
 * Dữ liệu này liên kết với một {@link DataType} cụ thể.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_data")
@EntityListeners(AuditingEntityListener.class)
public class SensorData {
    /**
     * Khóa chính của dữ liệu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Giá trị dữ liệu đo được.
     */
    @Column(name = "value", nullable = false)
    private Long value;

    /**
     * Thời gian ghi nhận của dữ liệu.
     */
    @Column(name = "recorded_at", nullable = false, updatable = false)
    private Instant recordedAt;

    /**
     * Thời gian tạo và cập nhật của dữ liệu.
     */
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Thời gian cập nhật cuối cùng của dữ liệu.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private Instant updatedAt;

    /**
     * Cảm biến liên kết với dữ liệu.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    /**
     * Loại dữ liệu liên kết với dữ liệu.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_type_id", nullable = false)
    private DataType dataType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        SensorData that = (SensorData) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
