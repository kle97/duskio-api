package com.duskio.features.profile;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {})
    Optional<Profile> findEntityById(@Nonnull Long id);
}
