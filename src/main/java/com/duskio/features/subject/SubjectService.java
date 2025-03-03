package com.duskio.features.subject;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional(readOnly = true)
    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Subject.class, id));
    }

    @Transactional(readOnly = true)
    public SubjectResponse findDTOById(Long id) {
        return subjectMapper.toSubjectResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<SubjectResponse> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable).map(subjectMapper::toSubjectResponse);
    }

    @Transactional
    public SubjectResponse save(SubjectRequest subjectRequest) {
        Subject transientSubject = subjectMapper.toSubject(subjectRequest);
        return subjectMapper.toSubjectResponse(subjectRepository.save(transientSubject));
    }

    @Transactional
    public SubjectResponse update(Long id, SubjectRequest subjectRequest) {
        Subject currentSubject = findById(id);
        return subjectMapper.toSubjectResponse(subjectMapper.toExistingSubject(subjectRequest, currentSubject));
    }

    @Transactional
    public void delete(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Subject.class, id);
        }
    }
}
