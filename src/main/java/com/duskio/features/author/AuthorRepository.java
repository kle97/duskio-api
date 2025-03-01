package com.duskio.features.author;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ListPagingAndSortingRepository<Author, Long> {

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "alternateNames.alternateName",
            "links.title",
            "links.url"
    })
    Page<Author> findAll(@Nonnull Pageable pageable);
}
