package com.example.smartroom.modules.room.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.example.smartroom.core.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.floor.entity.Floor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
@ToString(exclude = {"devices", "roomLans", "floor"})
public class Room extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", referencedColumnName = "id")
    private Floor floor;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Device> devices;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RoomLan> roomLans;
}
