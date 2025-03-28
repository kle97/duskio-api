package com.duskio.features.work.dto;

import com.duskio.features.rating.RatingResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder(toBuilder = true)
public class WorkPageResponse extends WorkResponse {

    private Set<RatingResponse> ratings;
}
