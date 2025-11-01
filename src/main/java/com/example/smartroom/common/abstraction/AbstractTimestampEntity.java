package com.example.smartroom.common.abstraction;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.smartroom.common.annotation.Filterable;
import com.example.smartroom.common.annotation.Sortable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractTimestampEntity {
	@CreatedDate
    @Sortable
    @Filterable
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Sortable
    @Filterable
    @Column(name = "updated_at", nullable = true)
    private Instant updatedAt;
}
