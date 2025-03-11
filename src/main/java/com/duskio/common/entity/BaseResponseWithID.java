package com.duskio.common.entity;

import com.duskio.common.jsonview.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class BaseResponseWithID {

    private Long id;

    @JsonView(BaseView.Admin.class)
    private String createdBy;

    @JsonView(BaseView.Admin.class)
    private LocalDateTime createdAt;

    @JsonView(BaseView.Admin.class)
    private String lastModifiedBy;

    @JsonView(BaseView.Admin.class)
    private LocalDateTime lastModifiedAt;

    @JsonView(BaseView.Admin.class)
    private Integer revision;
}
