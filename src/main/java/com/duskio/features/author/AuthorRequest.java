package com.duskio.features.author;

public record AuthorRequest(
        String authorName,
        String birthDate,
        String deathDate,
        String authorDate,
        String biography,
        String photo,
        String olKey
) {
}
