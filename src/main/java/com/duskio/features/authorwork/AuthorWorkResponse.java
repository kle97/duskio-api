package com.duskio.features.authorwork;

import com.duskio.common.entity.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AuthorWorkResponse extends BaseResponse {

    private Long workId;

    private Long authorId;
}
