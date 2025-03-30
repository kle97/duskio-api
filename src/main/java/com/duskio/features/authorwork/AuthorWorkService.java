package com.duskio.features.authorwork;

import com.duskio.common.exception.ResourceNotFoundException;
import com.duskio.features.author.Author;
import com.duskio.features.work.Work;
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
    public AuthorWork findById(Long authorId, Long workId) {
        return authorWorkRepository.findById(new AuthorWorkId(authorId, workId))
                                   .orElseThrow(() -> new ResourceNotFoundException(AuthorWork.class, Author.class, authorId, 
                                                                                    Work.class, workId));
    }

    @Transactional(readOnly = true)
    public AuthorWorkResponse findEntityById(Long authorId, Long workId) {
        return authorWorkMapper.toWorkAuthorResponse(findById(authorId, workId));
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
    public AuthorWorkResponse update(Long authorId, Long workId, AuthorWorkRequest authorWorkRequest) {
        AuthorWork authorWork = findById(authorId, workId);
        authorWork.setId(new AuthorWorkId(authorId, workId));
        return authorWorkMapper.toWorkAuthorResponse(authorWorkMapper.toExistingWorkAuthor(authorWorkRequest, authorWork));
    }

    @Transactional
    public void delete(Long authorId, Long workId) {
        AuthorWorkId workAuthorId = new AuthorWorkId(authorId, workId);
        if (authorWorkRepository.existsById(workAuthorId)) {
            authorWorkRepository.deleteById(workAuthorId);
        } else {
            throw new ResourceNotFoundException(AuthorWork.class, Author.class, authorId, Work.class, workId);
        }
    }
}
