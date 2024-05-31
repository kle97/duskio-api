package com.duskio.features.category;

import com.duskio.features.post.PostResponse;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public record Category(int categoryId, String name, @Nullable List<PostResponse> posts) {
    public Category {
        if (posts == null) {
            posts = new ArrayList<>();
        }
    }
}
