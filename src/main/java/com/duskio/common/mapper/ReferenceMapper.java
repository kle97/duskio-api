package com.duskio.common.mapper;

import com.duskio.common.entity.ID;
import org.mapstruct.TargetType;

public interface ReferenceMapper {

    <T extends ID> T idToEntity(Long id, @TargetType Class<T> entityClass);

    <T> T findById(Long id, Class<T> entityClass);
}
