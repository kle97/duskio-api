package com.duskio.features.worksubject;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkSubjectService {
    
    private final WorkSubjectRepository workSubjectRepository;
    private final WorkSubjectMapper workSubjectMapper;

    @Transactional(readOnly = true)
    public WorkSubject findById(Long workId, Long subjectId) {
        return workSubjectRepository.findById(new WorkSubjectId(workId, subjectId))
                                    .orElseThrow(() -> new ResourceNotFoundException(WorkSubject.class, Long.class, workId, Long.class, subjectId));
    }

    @Transactional(readOnly = true)
    public WorkSubjectResponse findDTOById(Long workId, Long subjectId) {
        return workSubjectMapper.toWorkSubjectResponse(findById(workId, subjectId));
    }

    @Transactional(readOnly = true)
    public Page<WorkSubjectResponse> findAll(Pageable pageable) {
        return workSubjectRepository.findAll(pageable).map(workSubjectMapper::toWorkSubjectResponse);
    }

    @Transactional
    public WorkSubjectResponse save(WorkSubjectRequest workSubjectRequest) {
        WorkSubjectId workSubjectId = new WorkSubjectId(workSubjectRequest.workId(), workSubjectRequest.subjectId());
        if (!workSubjectRepository.existsById(workSubjectId)) {
            WorkSubject workSubject = workSubjectMapper.toWorkSubject(workSubjectRequest);
            workSubject.setId(workSubjectId);
            return workSubjectMapper.toWorkSubjectResponse(workSubjectRepository.save(workSubject));
        } else {
            return findDTOById(workSubjectRequest.workId(), workSubjectRequest.subjectId());
        }
    }

    @Transactional
    public WorkSubjectResponse update(Long workId, Long subjectId, WorkSubjectRequest workSubjectRequest) {
        WorkSubject workSubject = findById(workId, subjectId);
        workSubject.setId(new WorkSubjectId(workSubjectRequest.workId(), workSubjectRequest.subjectId()));
        return workSubjectMapper.toWorkSubjectResponse(workSubjectMapper.toExistingWorkSubject(workSubjectRequest, workSubject));
    }

    @Transactional
    public void delete(Long workId, Long subjectId) {
        WorkSubjectId workSubjectId = new WorkSubjectId(workId, subjectId);
        if (workSubjectRepository.existsById(workSubjectId)) {
            workSubjectRepository.deleteById(workSubjectId);
        } else {
            throw new ResourceNotFoundException(WorkSubject.class, Long.class, workId, Long.class, subjectId);
        }
    }
}
