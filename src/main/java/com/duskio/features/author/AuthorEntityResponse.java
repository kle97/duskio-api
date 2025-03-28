package com.duskio.features.author;

import com.duskio.features.alternatename.AlternateNameResponse;
import com.duskio.features.authorlink.AuthorLinkResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorEntityResponse extends AuthorResponse {

    private Set<AlternateNameResponse> alternateNames;

    private Set<AuthorLinkResponse> links;
}
