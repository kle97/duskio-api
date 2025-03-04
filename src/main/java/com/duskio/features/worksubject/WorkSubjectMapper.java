package com.duskio.features.worksubject;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface WorkSubjectMapper {

    WorkSubjectMapper INSTANCE = Mappers.getMapper(WorkSubjectMapper.class);

    @Mapping(target = "workId", source = "id.workId")
    @Mapping(target = "subjectId", source = "id.subjectId")
    WorkSubjectResponse toWorkSubjectResponse(WorkSubject workSubject);
    
    WorkSubject toWorkSubject(WorkSubjectRequest workSubjectRequest);

    WorkSubject toExistingWorkSubject(WorkSubjectRequest workSubjectRequest, @MappingTarget WorkSubject workSubject);
}
