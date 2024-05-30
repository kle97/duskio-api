package com.duskio.features.post;

import com.duskio.features.category.CategoryResponse;
import com.duskio.features.postimage.PostImageDto;
import jakarta.annotation.Nullable;
import org.jdbi.v3.core.mapper.Nested;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record Post(int postId,
                   String title,
                   String description,
                   LocalDateTime dateTime,
                   @Nested CategoryResponse category, @Nullable List<PostImageDto> images) {
    public Post {
        if (images == null) {
            images = new ArrayList<>();
        }
    }
}
