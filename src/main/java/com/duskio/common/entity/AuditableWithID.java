package com.duskio.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "lastModifiedBy", "lastModifiedAt", "revision"},
        allowGetters = true
)
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableWithID extends ID {

    @CreatedBy
    @Column(nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;

    @Version
    @Column(nullable = false)
    private Integer revision;
}
