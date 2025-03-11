package com.duskio.features.author;

import com.duskio.common.entity.BaseResponseWithID;
import com.duskio.common.jsonview.BaseView;
import com.duskio.features.alternatename.AlternateNameResponse;
import com.duskio.features.authorlink.AuthorLinkResponse;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorResponse extends BaseResponseWithID {

    private String authorName;

    private String birthDate;

    private String deathDate;

    private String authorDate;

    private String biography;

    private String photo;

    private String olKey;

    private Set<AlternateNameResponse> alternateNames;

    private Set<AuthorLinkResponse> links;
}
