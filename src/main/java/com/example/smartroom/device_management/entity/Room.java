package com.example.smartroom.device_management.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.smartroom.common.abstraction.AbstractAuditableEntity;
import com.example.smartroom.common.annotation.Searchable;
import com.example.smartroom.common.annotation.Sortable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity đại diện cho một phòng vật lý trong hệ thống Smart Room.
 * Một phòng có thể chứa nhiều hub.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "room")
public class Room extends AbstractAuditableEntity{
	/**
	 * Khóa chính của phòng.
	 */
	@Id
	@Sortable
	@Searchable
	private String id;
	
	/**
	 * Tên phòng (ví dụ: "Phòng khách").
	 */
	@Sortable
	@Searchable
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Vị trí của phòng (ví dụ: "Tầng 1, Nhà A").
	 */
	@Sortable
	@Searchable
	@Column(name = "location")
	private String location;
	
	/**
	 * Mô tả phòng.
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * Danh sách các hub thuộc phòng này. Quan hệ OneToMany với Hub.
	 */
	@OneToMany(
		mappedBy = "room",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY
	)
	private Set<Hub> hubs = new HashSet<>();
}