package com.duskio.features.work;

public record WorkSimpleResponse(
        Long id,
        String title,
        String description,
        String olKey
) {
}
