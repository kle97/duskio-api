package com.duskio.features.authorlink;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface AuthorLinkMapper {

    AuthorLinkMapper INSTANCE = Mappers.getMapper(AuthorLinkMapper.class);

    AuthorLinkResponse toAuthorLinkResponse(AuthorLink authorLink);

    AuthorLink toAuthorLink(AuthorLinkRequest authorLinkRequest);

    AuthorLink toExistingAuthorLink(AuthorLinkRequest authorLinkRequest, @MappingTarget AuthorLink authorLink);
}
