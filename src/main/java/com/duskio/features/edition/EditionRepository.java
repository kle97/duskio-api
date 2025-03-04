package com.duskio.features.edition;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "publisher",
    })
    Page<Edition> findAll(@Nonnull Pageable pageable);
}
