package com.duskio.features.author;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorResponse toAuthorResponse(Author author);

    @Mapping(target = "alternateNames", ignore = true)
    @Mapping(target = "links", ignore = true)
    Author toAuthor(AuthorRequest authorRequest);

    @Mapping(target = "alternateNames", ignore = true)
    @Mapping(target = "links", ignore = true)
    Author toExistingAuthor(AuthorRequest authorRequest, @MappingTarget Author author);
}
