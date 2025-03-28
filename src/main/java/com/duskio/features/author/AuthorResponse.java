package com.duskio.features.author;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorResponse extends AuthorSimpleResponse {

    private String birthDate;

    private String deathDate;

    private String authorDate;

    private String biography;

    private String photo;

    private String olKey;
}
