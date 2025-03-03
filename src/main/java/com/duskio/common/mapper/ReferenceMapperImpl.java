package com.duskio.common.mapper;

import com.duskio.common.entity.ID;
import com.duskio.common.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.TargetType;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.jpa.AvailableHints.HINT_CACHEABLE;

public class ReferenceMapperImpl implements ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T extends ID> T idToEntity(Long id, @TargetType Class<T> entityClass) {
        return findById(id, entityClass);
    }

    @Override
    public <T> T findById(Long id, Class<T> entityClass) {
        if (id == null) {
            return null;
        }

        Map<String, Object> properties = new HashMap<>();
//        properties.put(HINT_CACHEABLE, true);
        T entity = entityManager.find(entityClass, id, properties);

        if (entity != null) {
            return entity;
        } else {
            throw new ResourceNotFoundException(entityClass, id);
        }
    }
}
