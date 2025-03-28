package com.duskio.common.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class BaseResponseWithID extends BaseResponse {

    private Long id;
}
