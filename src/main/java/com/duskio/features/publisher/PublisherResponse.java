package com.duskio.features.publisher;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class PublisherResponse extends BaseResponseWithID {

    private String publisherName;
}
