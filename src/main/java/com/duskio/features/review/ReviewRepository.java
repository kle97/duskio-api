package com.duskio.features.review;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "work"
    })
    Optional<Review> findEntityById(@Nonnull ReviewId reviewId);
}
