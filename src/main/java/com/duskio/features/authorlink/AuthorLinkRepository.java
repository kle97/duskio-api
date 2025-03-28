package com.duskio.features.authorlink;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorLinkRepository extends JpaRepository<AuthorLink, Long> {

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "author"
    })
    Optional<AuthorLink> findEntityById(@Nonnull Long id);
}
