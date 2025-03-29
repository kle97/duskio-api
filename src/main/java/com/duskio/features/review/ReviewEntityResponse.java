package com.duskio.features.review;

import com.duskio.features.edition.dto.EditionSimpleResponse;
import com.duskio.features.profile.ProfileResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ReviewEntityResponse extends ReviewResponse {

    private ProfileResponse profileResponse;

    private EditionSimpleResponse edition;
}
