package com.example.smartroom.modules.sensor_type.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.example.smartroom.core.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.modules.sensor.entity.Sensor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_type")
@ToString(exclude = {"sensors", "sensorTypeLans"})
public class SensorType extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(mappedBy = "sensorType", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

    @OneToMany(mappedBy = "sensorType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SensorTypeLan> sensorTypeLans;
}
