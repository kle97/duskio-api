package com.duskio.features.author;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
    }

    @Transactional(readOnly = true)
    public AuthorResponse findDTOById(Long id) {
        return authorMapper.toAuthorResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<AuthorResponse> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toAuthorResponse);
    }

    @Transactional
    public AuthorResponse save(AuthorRequest authorRequest) {
        Author transientAuthor = authorMapper.toAuthor(authorRequest);
        return authorMapper.toAuthorResponse(authorRepository.save(transientAuthor));
    }

    @Transactional
    public AuthorResponse update(Long id, AuthorRequest authorRequest) {
        Author currentAuthor = findById(id);
        return authorMapper.toAuthorResponse(authorMapper.toExistingAuthor(authorRequest, currentAuthor));
    }

    @Transactional
    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Author.class, id);
        }
    }
}
