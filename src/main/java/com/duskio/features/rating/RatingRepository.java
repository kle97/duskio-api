package com.duskio.features.rating;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends ListPagingAndSortingRepository<Rating, Long>, ListCrudRepository<Rating, Long> {
}
