package com.duskio.features.rating;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class RatingResponse extends BaseResponseWithID {

    private Integer score;
}
