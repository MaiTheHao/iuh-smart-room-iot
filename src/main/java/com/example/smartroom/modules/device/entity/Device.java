package com.example.smartroom.modules.device.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.example.smartroom.core.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.modules.room.entity.Room;
import com.example.smartroom.modules.sensor.entity.Sensor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device")
@ToString(exclude = {"room", "deviceLans", "sensors", "gateway", "childDevices"})
public class Device extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_gateway")
    private Boolean isGateway;

    @Column(name = "model")
    private String model;

    @Column(name = "mac")
    private String mac;

    @Column(name = "ip")
    private String ip;

    @Column(name = "connection_protocol")
    private String connectionProtocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DeviceLan> deviceLans;

    // Self-referencing gateway relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", referencedColumnName = "id")
    private Device gateway;

    @OneToMany(mappedBy = "gateway", fetch = FetchType.LAZY)
    private List<Device> childDevices;
}
