package com.example.smartroom.modules.sensor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.example.smartroom.core.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.sensor_reading.entity.SensorReading;
import com.example.smartroom.modules.sensor_type.entity.SensorType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor")
@ToString(exclude = {"device", "sensorType", "sensorReadings", "sensorLans"})
public class Sensor extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "connection_protocol")
    private String connectionProtocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_type_id", referencedColumnName = "id")
    private SensorType sensorType;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<SensorReading> sensorReadings;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SensorLan> sensorLans;
}
