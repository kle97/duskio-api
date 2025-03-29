package com.duskio.features.work;

import com.duskio.common.mapper.ReferenceMapper;
import com.duskio.features.work.dto.WorkEntityResponse;
import com.duskio.features.work.dto.WorkRequest;
import com.duskio.features.work.dto.WorkResponse;
import com.duskio.features.work.dto.WorkSimpleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface WorkMapper {

    WorkMapper INSTANCE = Mappers.getMapper(WorkMapper.class);
    
    WorkResponse toWorkResponse(Work work);

    WorkEntityResponse toWorkEntityResponse(Work work);

    WorkSimpleResponse toWorkSimpleResponse(Work work);
    
    @Mapping(target = "subjects", ignore = true)
    Work toWork(WorkRequest workRequest);

    @Mapping(target = "subjects", ignore = true)
    Work toExistingWork(WorkRequest workRequest, @MappingTarget Work work);
}
