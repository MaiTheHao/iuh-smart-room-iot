package com.example.smartroom.core.common.abstraction;

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
    @Column(name = "created_by", nullable = true)
    private Long createdBy;

//	@LastModifiedBy
    @Column(name = "updated_by", nullable = true)
    private Long updatedBy;

    @Version
    @Column(name = "v")
    private Integer version;
}
