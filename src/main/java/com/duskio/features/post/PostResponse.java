package com.duskio.features.post;

import com.duskio.features.postimage.PostImageResponse;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record PostResponse(int postId,
                           
                           @Nullable 
                           String title,
                           
                           @Nullable 
                           String description,
                           
                           @Nullable 
                           LocalDateTime createdAt,
                           
                           @Nullable 
                           List<PostImageResponse> images) {
    public PostResponse {
        images = images == null ? new ArrayList<>() : images;
    }
}
