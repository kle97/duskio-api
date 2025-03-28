package com.duskio.features.rating;

import com.duskio.features.work.dto.WorkSimpleResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class RatingEntityResponse extends RatingResponse {

    private WorkSimpleResponse work;
}
