package com.duskio.features.work;

import com.duskio.features.rating.RatingResponse;
import com.duskio.features.subject.SubjectResponse;

import java.util.Set;

public record WorkEntityResponse(
        Long id,
        String title,
        String olKey,
        Set<RatingResponse> ratings,
        Set<SubjectResponse> subjects
) {
}
