package com.example.smartroom.device_management.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity đại diện cho một phòng vật lý trong hệ thống Smart Room.
 * Một phòng có thể chứa nhiều hub.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room extends AbstractAuditableEntity{
	/**
	 * Khóa chính của phòng.
	 */
	@Id
	private String id;
	
	/**
	 * Tên phòng (ví dụ: "Phòng khách").
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Vị trí của phòng (ví dụ: "Tầng 1, Nhà A").
	 */
	@Column(name = "location")
	private String location;
	
	/**
	 * Mô tả phòng.
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * Danh sách các hub thuộc phòng này.
	 */
	@OneToMany(
		mappedBy = "room",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY
	)
	private Set<Hub> hubs = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Room room = (Room) o;
		
		return id != null && id.equals(room.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}