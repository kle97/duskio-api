package com.duskio.features.author;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ListPagingAndSortingRepository<Author, Integer> {
}
