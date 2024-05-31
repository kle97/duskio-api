package com.duskio.features.post;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record PostResponse(int postId,
                           @Nullable String title,
                           @Nullable String description,
                           @Nullable LocalDateTime dateTime) {
}
