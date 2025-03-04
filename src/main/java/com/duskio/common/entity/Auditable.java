package com.duskio.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "lastModifiedBy", "lastModifiedAt", "revision"},
        allowGetters = true
)
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Version
    private Integer revision;
}
