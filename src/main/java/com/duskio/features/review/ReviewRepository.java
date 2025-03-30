package com.duskio.features.review;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Optional<Review> findByProfileIdAndEditionId(Long profileId, Long editionId);

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "profile", "edition"
    })
    Optional<Review> findEntityByProfile_IdAndEdition_Id(@Nonnull Long profileId, @Nonnull Long editionId);
    
    boolean existsByProfile_IdAndEdition_Id(Long profileId, Long editionId);
    
    void deleteByProfile_IdAndEdition_Id(Long profileId, Long editionId);
}
