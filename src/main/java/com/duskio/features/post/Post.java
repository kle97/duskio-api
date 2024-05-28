package com.duskio.features.post;

import java.time.LocalDateTime;

public record Post(int id, String title, String description, LocalDateTime dateTime, int categoryId) {
}
