package com.duskio.features.work;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Override @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths={})
    Page<Work> findAll(@Nonnull Pageable pageable);

    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {
            "authors", "subjects"
    })
    Optional<Work> findEntityById(@Nonnull Long id);
}
