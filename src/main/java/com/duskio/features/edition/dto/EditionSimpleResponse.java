package com.duskio.features.edition.dto;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class EditionSimpleResponse extends BaseResponseWithID {

    private String title;

    private String subtitle;
}
