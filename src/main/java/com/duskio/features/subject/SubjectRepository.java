package com.duskio.features.subject;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends ListPagingAndSortingRepository<Subject, Long>, ListCrudRepository<Subject, Long> {
}
