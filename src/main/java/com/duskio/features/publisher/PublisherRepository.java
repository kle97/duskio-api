package com.duskio.features.publisher;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends ListPagingAndSortingRepository<Publisher, Long>, ListCrudRepository<Publisher, Long> {
}
