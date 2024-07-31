package com.duskio.features.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryDto(@NotNull @Min(1) Integer categoryId,
                          @NotBlank @Size(max = 255) String name) {
}
