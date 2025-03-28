package com.duskio.features.alternatename;

import com.duskio.features.author.AuthorSimpleResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AlternateNameEntityResponse extends AlternateNameResponse {

    private AuthorSimpleResponse author;
}
