package com.duskio.features.rating;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "work"
    })
    Optional<Rating> findEntityById(@Nonnull Long id);
}
