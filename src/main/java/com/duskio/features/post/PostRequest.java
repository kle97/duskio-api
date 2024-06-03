package com.duskio.features.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequest(@NotBlank @Size(max = 255) String title,
                          @NotEmpty @Size(max = 4000) String description, 
                          @NotNull Integer categoryId) {
}
