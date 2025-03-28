package com.duskio.features.authorlink;

import com.duskio.features.author.AuthorSimpleResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorLinkEntityResponse extends AuthorLinkResponse {

    private AuthorSimpleResponse author;
}
