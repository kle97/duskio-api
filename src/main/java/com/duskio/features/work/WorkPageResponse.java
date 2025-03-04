package com.duskio.features.work;

import com.duskio.features.rating.RatingResponse;

import java.util.Set;

public record WorkPageResponse(
        Long id, 
        String title, 
        String olKey,
        Set<RatingResponse> ratings
) {
}
