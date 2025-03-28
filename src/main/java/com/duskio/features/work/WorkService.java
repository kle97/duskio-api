package com.duskio.features.work;

import com.duskio.common.exception.ResourceNotFoundException;
import com.duskio.features.work.dto.WorkEntityResponse;
import com.duskio.features.work.dto.WorkPageResponse;
import com.duskio.features.work.dto.WorkRequest;
import com.duskio.features.work.dto.WorkResponse;
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
    public WorkEntityResponse findEntityById(Long id) {
        return workMapper.toWorkEntityResponse(workRepository.findEntityById(id)
                                                             .orElseThrow(() -> new ResourceNotFoundException(Work.class, id)));
    }

    @Transactional(readOnly = true)
    public Page<WorkPageResponse> findAll(Pageable pageable) {
        return workRepository.findAll(pageable).map(workMapper::toWorkPageResponse);
    }

    @Transactional
    public WorkResponse save(WorkRequest workRequest) {
        Work transientWork = workMapper.toWork(workRequest);
        return workMapper.toWorkResponse(workRepository.save(transientWork));
    }

    @Transactional
    public WorkResponse update(Long id, WorkRequest workRequest) {
        Work currentWork = findById(id);
        return workMapper.toWorkResponse(workMapper.toExistingWork(workRequest, currentWork));
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
