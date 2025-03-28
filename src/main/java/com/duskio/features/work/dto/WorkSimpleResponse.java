package com.duskio.features.work.dto;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class WorkSimpleResponse extends BaseResponseWithID {

    private String title;
}
