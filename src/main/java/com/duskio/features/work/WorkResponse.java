package com.duskio.features.work;

import com.duskio.features.alternatename.AlternateNameResponse;
import com.duskio.features.authorlink.AuthorLinkResponse;
import com.duskio.features.rating.RatingResponse;
import com.duskio.features.worksubject.WorkSubject;

import java.util.Set;

public record WorkResponse(
        Long id, 
        String title, 
        String olKey,
        Set<RatingResponse> ratings,
        Set<WorkSubject> subjects
) {
}
