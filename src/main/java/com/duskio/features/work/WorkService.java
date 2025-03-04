package com.duskio.features.work;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final WorkMapper workMapper;

    @Transactional(readOnly = true)
    public Work findById(Long id) {
        return workRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Work.class, id));
    }

    @Transactional(readOnly = true)
    public WorkResponse findDTOById(Long id) {
        return workMapper.toWorkResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<WorkPageResponse> findAll(Pageable pageable) {
        return workRepository.findAll(pageable).map(workMapper::toWorkPageResponse);
    }

    @Transactional
    public WorkSimpleResponse save(WorkRequest workRequest) {
        Work transientWork = workMapper.toWork(workRequest);
        return workMapper.toWorkSimpleResponse(workRepository.save(transientWork));
    }

    @Transactional
    public WorkSimpleResponse update(Long id, WorkRequest workRequest) {
        Work currentWork = findById(id);
        return workMapper.toWorkSimpleResponse(workMapper.toExistingWork(workRequest, currentWork));
    }

    @Transactional
    public void delete(Long id) {
        if (workRepository.existsById(id)) {
            workRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Work.class, id);
        }
    }
}
