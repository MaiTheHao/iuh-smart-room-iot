package com.example.smartroom.device_management.entity.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Khóa chính tổng hợp cho entity nối nhiều-nhiều giữa sensor và data_type.
 * Đại diện cho mối quan hệ giữa một cảm biến (sensor) và một loại dữ liệu (data_type) mà cảm biến đó có thể đo.
 *
 * Bao gồm:
 * - sensorId: Định danh logic của cảm biến.
 * - dataTypeId: Định danh loại dữ liệu mà cảm biến cung cấp.
 *
 * Được sử dụng trong entity SensorDataType để đảm bảo tính duy nhất cho từng cặp cảm biến - loại dữ liệu.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode(
    of = {
        "sensorId",
        "dataTypeId"
    }, 
    callSuper = false
)
public class SensorDataTypeId implements Serializable {
	
    private static final long serialVersionUID = -2449621564662260851L;
    
	private String sensorId;
	
    private Long dataTypeId;
}
