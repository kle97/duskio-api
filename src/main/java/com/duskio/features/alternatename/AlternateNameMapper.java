package com.duskio.features.alternatename;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface AlternateNameMapper {

    AlternateNameMapper INSTANCE = Mappers.getMapper(AlternateNameMapper.class);

    AlternateNameResponse toAlternateNameResponse(AlternateName alternateName);

    AlternateName toAlternateName(AlternateNameRequest alternateNameRequest);

    AlternateName toExistingAlternateName(AlternateNameRequest alternateNameRequest, @MappingTarget AlternateName alternateName);
}
