package com.duskio.features.authorwork;

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
public class AuthorWorkService {

    private final AuthorWorkRepository authorWorkRepository;
    private final AuthorWorkMapper authorWorkMapper;

    @Transactional(readOnly = true)
    public AuthorWork findById(Long workId, Long authorId) {
        return authorWorkRepository.findById(new AuthorWorkId(authorId, workId))
                                   .orElseThrow(() -> new ResourceNotFoundException(AuthorWork.class, Long.class, workId, 
                                                                                    Long.class, authorId));
    }

    @Transactional(readOnly = true)
    public AuthorWorkResponse findDTOById(Long workId, Long authorId) {
        return authorWorkMapper.toWorkAuthorResponse(findById(workId, authorId));
    }

    @Transactional(readOnly = true)
    public Page<AuthorWorkResponse> findAll(Pageable pageable) {
        return authorWorkRepository.findAll(pageable).map(authorWorkMapper::toWorkAuthorResponse);
    }

    @Transactional
    public AuthorWorkResponse save(AuthorWorkRequest authorWorkRequest) {
        AuthorWork authorWork = authorWorkMapper.toWorkAuthor(authorWorkRequest);
        authorWork.setId(new AuthorWorkId(authorWorkRequest.authorId(), authorWorkRequest.workId()));
        return authorWorkMapper.toWorkAuthorResponse(authorWorkRepository.save(authorWork));
    }

    @Transactional
    public AuthorWorkResponse update(Long workId, Long authorId, AuthorWorkRequest authorWorkRequest) {
        AuthorWork authorWork = findById(workId, authorId);
        authorWork.setId(new AuthorWorkId(authorId, workId));
        return authorWorkMapper.toWorkAuthorResponse(authorWorkMapper.toExistingWorkAuthor(authorWorkRequest, authorWork));
    }

    @Transactional
    public void delete(Long workId, Long authorId) {
        AuthorWorkId workAuthorId = new AuthorWorkId(authorId, workId);
        if (authorWorkRepository.existsById(workAuthorId)) {
            authorWorkRepository.deleteById(workAuthorId);
        } else {
            throw new ResourceNotFoundException(AuthorWork.class, Long.class, workId, Long.class, authorId);
        }
    }
}
