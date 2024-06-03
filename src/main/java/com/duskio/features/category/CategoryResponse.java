package com.duskio.features.category;

import com.duskio.features.post.PostResponse;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public record CategoryResponse(int categoryId, 
                               @Nullable String name,
                               @Nullable List<PostResponse> posts) {
    public CategoryResponse {
        posts = posts == null ? new ArrayList<>() : posts;
    }
}
