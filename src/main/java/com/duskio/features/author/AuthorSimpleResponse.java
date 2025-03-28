package com.duskio.features.author;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorSimpleResponse extends BaseResponseWithID {

    private String authorName;
}
