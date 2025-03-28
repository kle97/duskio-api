package com.duskio.features.edition.dto;

import com.duskio.features.work.dto.WorkSimpleResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class EditionEntityResponse extends EditionResponse {

    private WorkSimpleResponse work;
}
