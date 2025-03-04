package com.duskio.common.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.util.List;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;

    public CustomRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    public Page<ID> findEntityIds(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ID> criteriaQuery = criteriaBuilder.createQuery(this.entityInformation.getIdType());
        Root<T> root = criteriaQuery.from(this.getDomainClass());

        // Get the entities ID only
        criteriaQuery.select((Path<ID>) root.get(this.entityInformation.getIdAttribute()));

        // Update Sorting
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        if (sort.isSorted()) {
            criteriaQuery.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));
        }

        TypedQuery<ID> typedQuery = this.entityManager.createQuery(criteriaQuery);

        // Update Pagination attributes
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(typedQuery.getResultList(), pageable,
                                              () -> executeCountQuery(this.getCountQuery(null, this.getDomainClass())));
    }

    protected static long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }
}
