package com.duskio.features.publisher;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherResponse toPublisherResponse(Publisher publisher);
    
    Publisher toPublisher(PublisherResponse publisherRequest);
    
    Publisher toExistingPublisher(PublisherRequest publisherRequest, @MappingTarget Publisher publisher);
}
