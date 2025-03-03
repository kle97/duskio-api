package com.duskio.features.subject;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectResponse toSubjectResponse(Subject subject);
    
    Subject toSubject(SubjectRequest subjectRequest);
    
    Subject toExistingSubject(SubjectRequest subjectRequest, @MappingTarget Subject subject);
}
