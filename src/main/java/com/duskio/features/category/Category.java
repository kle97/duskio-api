package com.duskio.features.category;

import com.duskio.features.post.PostDto;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public record Category(int categoryId, String name, @Nullable List<PostDto> posts) {
    public Category {
        if (posts == null) {
            posts = new ArrayList<>();
        }
    }
}
