package com.duskio.features.alternatename;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor @Slf4j
public class AlternateNameService {
    
    private final AlternateNameRepository alternateNameRepository;
    private final AlternateNameMapper alternateNameMapper;

    @Transactional(readOnly = true)
    public AlternateName findById(Long id) {
        return alternateNameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AlternateName.class, id));
    }

    @Transactional
    public AlternateNameResponse save(AlternateNameRequest alternateNameRequest) {
        AlternateName alternateName = alternateNameMapper.toAlternateName(alternateNameRequest);
        return alternateNameMapper.toAlternateNameResponse(alternateNameRepository.save(alternateName));
    }

    @Transactional
    public AlternateNameResponse update(Long id, AlternateNameRequest alternateNameRequest) {
        AlternateName alternateName = findById(id);
        return alternateNameMapper.toAlternateNameResponse(alternateNameMapper.toExistingAlternateName(alternateNameRequest, alternateName));
    }

    @Transactional
    public void delete(Long id) {
        if (alternateNameRepository.existsById(id)) {
            alternateNameRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(AlternateName.class, id);
        }
    }
}
