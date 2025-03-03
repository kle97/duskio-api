package com.duskio.features.author;

import com.duskio.features.alternatename.AlternateNameResponse;
import com.duskio.features.authorlink.AuthorLinkResponse;

import java.util.Set;

public record AuthorResponse(
        Long id,
        String  birthDate,
        String deathDate,
        String authorDate,
        String biography,
        String photo,
        String olKey,
        Set<AlternateNameResponse> alternateNames,
        Set<AuthorLinkResponse> links
) {
}
