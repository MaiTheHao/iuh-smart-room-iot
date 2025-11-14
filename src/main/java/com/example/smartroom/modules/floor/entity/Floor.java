package com.example.smartroom.modules.floor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.example.smartroom.core.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.modules.room.entity.Room;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "floor")
@ToString(exclude = {"rooms", "floorLans"})
public class Floor extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "level")
    private Integer level;

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FloorLan> floorLans;
}
