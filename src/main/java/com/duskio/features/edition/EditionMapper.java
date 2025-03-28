package com.duskio.features.edition;

import com.duskio.common.mapper.ReferenceMapper;
import com.duskio.features.edition.dto.EditionRequest;
import com.duskio.features.edition.dto.EditionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface EditionMapper {

    EditionMapper INSTANCE = Mappers.getMapper(EditionMapper.class);

    EditionResponse toEditionResponse(Edition edition);
    
    Edition toEdition(EditionRequest editionRequest);
    
    Edition toExistingEdition(EditionRequest editionRequest, @MappingTarget Edition edition);
}
