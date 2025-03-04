package com.duskio.features.edition;

import com.duskio.common.exception.ResourceNotFoundException;
import com.duskio.common.service.EnglishDictionary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EditionService {

    private final EditionRepository editionRepository;
    private final EditionMapper editionMapper;
    private final EnglishDictionary englishDictionary;

    @Transactional(readOnly = true)
    public Edition findById(Long id) {
        return editionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Edition.class, id));
    }

    @Transactional(readOnly = true)
    public EditionResponse findDTOById(Long id) {
        return editionMapper.toEditionResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<EditionResponse> findAll(Pageable pageable) {
        return editionRepository.findAll(pageable).map(editionMapper::toEditionResponse);
    }

    @Transactional
    public EditionResponse save(EditionRequest editionRequest) {
        Edition edition = editionMapper.toEdition(editionRequest);
        edition.setGrade(editionGrade(edition));
        return editionMapper.toEditionResponse(editionRepository.save(edition));
    }

    @Transactional
    public EditionResponse update(Long id, EditionRequest editionRequest) {
        Edition edition = findById(id);
        edition.setGrade(editionGrade(edition));
        return editionMapper.toEditionResponse(editionMapper.toExistingEdition(editionRequest, edition));
    }

    @Transactional
    public void delete(Long id) {
        if (editionRepository.existsById(id)) {
            editionRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Edition.class, id);
        }
    }
    
    private int editionGrade(Edition edition) {
        int grade = 0;
        if (edition.getDescription() != null && !edition.getDescription().isBlank()) {
            grade += 5;
            if (edition.getDescription().length() > 20) {
                grade += 20;
            }
        }
        grade += edition.getCover() != null && !edition.getCover().isBlank() ? 15 : 0;
        grade += edition.getIsbn10() != null && !edition.getIsbn10().isBlank() ? 20 : 0;
        grade += edition.getIsbn13() != null && !edition.getIsbn13().isBlank() ? 20 : 0;
        grade += edition.getPublisher() != null ? 10 : 0;
        grade += edition.getPublishDate() != null && !edition.getPublishDate().isBlank() ? 5 : 0;
        grade += edition.getNumberOfPages() != null ? 5 : 0;
        grade += isEnglishEdition(edition) ? 100 : 0;
        
        return grade;
    }
    
    private boolean isEnglishEdition(Edition edition) {
        boolean isInEnglish = false;
        if (edition.getTitle() != null) {
            String[] tokens = edition.getTitle().split(" ");
            int englishWordCount = 0;
            for (String token : tokens) {
                if (englishDictionary.contains(token.trim().toLowerCase())) {
                    englishWordCount++;
                }
                if (englishWordCount >= 3) {
                    isInEnglish = true;
                    break;
                }
            }
        }
        return isInEnglish;
    }
}
