package com.duskio.features.category;

import jakarta.annotation.Nullable;

public record CategoryResponse(int categoryId, @Nullable String name) {
}
