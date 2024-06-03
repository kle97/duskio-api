package com.duskio.features.postimage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostImageRequest(@NotBlank @Size(max = 1000) String imageLink,
                               @NotNull int postId) {
}
