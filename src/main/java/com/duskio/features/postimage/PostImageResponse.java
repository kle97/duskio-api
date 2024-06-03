package com.duskio.features.postimage;

import jakarta.annotation.Nullable;

public record PostImageResponse(int postImageId,
                                @Nullable String imageLink,
                                @Nullable Integer postId) {
}
