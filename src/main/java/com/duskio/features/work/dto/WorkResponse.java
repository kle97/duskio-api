package com.duskio.features.work.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class WorkResponse extends WorkSimpleResponse {

    private String description;

    private String olKey;
}
