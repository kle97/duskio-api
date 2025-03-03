package com.duskio.features.work;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkRepository extends ListPagingAndSortingRepository<Work, Long>, ListCrudRepository<Work, Long> {

    @Override @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "ratings.score"
    })
    Page<Work> findAll(@Nonnull Pageable pageable);
}
