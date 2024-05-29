package com.duskio.features.post;

import java.time.LocalDateTime;

public record PostDto(int postId, String title, String description, LocalDateTime dateTime) {
}
