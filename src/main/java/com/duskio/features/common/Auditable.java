package com.duskio.features.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "lastModifiedBy", "lastModifiedAt"},
        allowGetters = true
)
public abstract class Auditable {

    @CreatedBy
    @InsertOnlyProperty
    private String createdBy;

    @CreatedDate
    @InsertOnlyProperty
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
