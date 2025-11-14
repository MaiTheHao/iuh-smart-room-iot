package com.example.smartroom.modules.sensor_reading.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.example.smartroom.modules.sensor.entity.Sensor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_reading", indexes = {
    @Index(name = "idx_sensor_id", columnList = "sensor_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_sensor_timestamp", columnList = "sensor_id,timestamp")
})
@ToString(exclude = {"sensor"})
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "temp_c")
    private Double tempC;

    @Column(name = "volt")
    private Double volt;

    @Column(name = "ampe")
    private Double ampe;

    @Column(name = "watt")
    private Double watt;

    @Column(name = "watt_hour")
    private Double wattHour;

    @Column(name = "hz")
    private Double hz;

    @Column(name = "power_factor")
    private Double powerFactor;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id", nullable = false)
    private Sensor sensor;
}
