package com.duskio.features.review;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ReviewResponse extends BaseResponseWithID {

    private Integer score;

    private String review;
}
