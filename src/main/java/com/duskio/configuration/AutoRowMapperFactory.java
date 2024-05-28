package com.duskio.configuration;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.RowMapperFactory;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Reference: https://github.com/jdbi/jdbi/issues/1822#issuecomment-1407596793
 */
public class AutoRowMapperFactory implements RowMapperFactory {
    
    @Override
    public Optional<RowMapper<?>> build(Type type, ConfigRegistry config) {
        if ((type instanceof Class<?> clazz) && clazz.isRecord()) {
            return ConstructorMapper.factory(clazz).build(type, config);
        } else {
            return Optional.empty();
        }
    }
}
