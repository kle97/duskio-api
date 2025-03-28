package com.duskio.features.work.dto;

import com.duskio.features.author.AuthorResponse;
import com.duskio.features.rating.RatingResponse;
import com.duskio.features.subject.SubjectResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder(toBuilder = true)
public class WorkEntityResponse extends WorkResponse {

    private Set<AuthorResponse> authors;

    private Set<SubjectResponse> subjects;

    private Set<RatingResponse> ratings;
}
