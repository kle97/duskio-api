package com.duskio.features.alternatename;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlternateNameRepository extends JpaRepository<AlternateName, Long> {

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "author"
    })
    Optional<AlternateName> findEntityById(@Nonnull Long id);
}
