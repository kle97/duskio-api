package com.duskio.features.profile;

import com.duskio.common.entity.BaseResponseWithID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ProfileResponse extends BaseResponseWithID {

    private String userId;

    private String username;

    private String displayName;

    private String profilePicture;
}
