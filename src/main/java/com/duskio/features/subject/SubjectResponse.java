package com.duskio.features.subject;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class SubjectResponse extends BaseResponseWithID {

    private String subjectName;
}
