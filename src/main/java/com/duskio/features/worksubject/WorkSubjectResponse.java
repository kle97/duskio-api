package com.duskio.features.worksubject;

import com.duskio.common.entity.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class WorkSubjectResponse extends BaseResponse {

    private Long workId;

    private Long subjectId;
}
