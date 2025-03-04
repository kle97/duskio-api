package com.duskio.features.authorwork;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface AuthorWorkMapper {

    AuthorWorkMapper INSTANCE = Mappers.getMapper(AuthorWorkMapper.class);

    @Mapping(target = "workId", source = "id.workId")
    @Mapping(target = "authorId", source = "id.authorId")
    AuthorWorkResponse toWorkAuthorResponse(AuthorWork workAuthor);
    
    AuthorWork toWorkAuthor(AuthorWorkRequest workAuthorRequest);

    AuthorWork toExistingWorkAuthor(AuthorWorkRequest workAuthorRequest, @MappingTarget AuthorWork workAuthor);
}
