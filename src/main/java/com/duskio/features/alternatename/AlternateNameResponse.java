package com.duskio.features.alternatename;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class AlternateNameResponse extends BaseResponseWithID {

    private String alternateName;
}
