package com.duskio.features.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategorySaveDto(@NotBlank @Size(max = 255) String name) {
}
