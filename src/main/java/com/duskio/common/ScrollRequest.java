package com.duskio.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ScrollPosition;

import java.util.List;

@Slf4j
public record ScrollRequest(List<Object> cursor, 
                            @NotNull ScrollPosition.Direction direction, 
                            @NotNull @Min(1) int limit,
                            @NotEmpty List<String> sort) {
}
