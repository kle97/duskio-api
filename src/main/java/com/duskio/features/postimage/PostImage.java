package com.duskio.features.postimage;

import com.duskio.features.post.PostResponse;
import org.jdbi.v3.core.mapper.Nested;

public record PostImage(int postImageId, String imageLink, @Nested PostResponse post) {
}
