package com.example.smartroom.device_management.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SensorDataTypeId implements Serializable {
	
    private static final long serialVersionUID = -2449621564662260851L;
    
	private String sensorId;
	
    private Long dataTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDataTypeId that = (SensorDataTypeId) o;
        return Objects.equals(sensorId, that.sensorId) &&
               Objects.equals(dataTypeId, that.dataTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, dataTypeId);
    }
}
