package com.example.smartroom.common.abstraction;

import com.example.smartroom.common.annotation.Filterable;
import com.example.smartroom.common.annotation.Sortable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractAuditableEntity extends AbstractTimestampEntity {
//	@CreatedBy
    @Sortable
    @Column(name = "created_by", nullable = true)
    private Long createdBy;

//	@LastModifiedBy
    @Sortable
    @Column(name = "updated_by", nullable = true)
    private Long updatedBy;

    @Version
    @Sortable
    @Filterable
    @Column(name = "v")
    private Integer version;
}
