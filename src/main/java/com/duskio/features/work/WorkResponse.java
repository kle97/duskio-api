package com.duskio.features.work;

import com.duskio.features.author.AuthorResponse;
import com.duskio.features.rating.RatingResponse;
import com.duskio.features.subject.SubjectResponse;

import java.util.Set;

public record WorkResponse(
        Long id,
        String title,
        String olKey,
        Set<AuthorResponse> authors,
        Set<SubjectResponse> subjects,
        Set<RatingResponse> ratings
) {
}
