package com.duskio.features.authorlink;

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
public class AuthorLinkService {
    
    private final AuthorLinkRepository authorLinkRepository;
    private final AuthorLinkMapper authorLinkMapper;

    @Transactional(readOnly = true)
    public AuthorLink findById(Long id) {
        return authorLinkRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AuthorLink.class, id));
    }

    @Transactional(readOnly = true)
    public AuthorLinkEntityResponse findEntityById(Long id) {
        AuthorLink entity = authorLinkRepository.findEntityById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException(AuthorLink.class, id));
        return authorLinkMapper.toAuthorLinkEntityResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<AuthorLinkResponse> findAll(Pageable pageable) {
        return authorLinkRepository.findAll(pageable).map(authorLinkMapper::toAuthorLinkResponse);
    }

    @Transactional
    public AuthorLinkResponse save(AuthorLinkRequest authorLinkRequest) {
        AuthorLink authorLink = authorLinkMapper.toAuthorLink(authorLinkRequest);
        return authorLinkMapper.toAuthorLinkResponse(authorLinkRepository.save(authorLink));
    }

    @Transactional
    public AuthorLinkResponse update(Long id, AuthorLinkRequest authorLinkRequest) {
        return authorLinkMapper.toAuthorLinkResponse(authorLinkMapper.toExistingAuthorLink(authorLinkRequest, findById(id)));
    }

    @Transactional
    public void delete(Long id) {
        if (authorLinkRepository.existsById(id)) {
            authorLinkRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(AuthorLink.class, id);
        }
    }
}
