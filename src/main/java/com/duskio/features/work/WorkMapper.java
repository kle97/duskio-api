package com.duskio.features.work;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface WorkMapper {

    WorkMapper INSTANCE = Mappers.getMapper(WorkMapper.class);
    
    WorkResponse toWorkResponse(Work work);
    
    WorkPageResponse toWorkPageResponse(Work work);

    WorkSimpleResponse toWorkSimpleResponse(Work work);
    
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    Work toWork(WorkRequest workRequest);

    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    Work toExistingWork(WorkRequest workRequest, @MappingTarget Work work);
}
