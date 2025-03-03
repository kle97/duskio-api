package com.duskio.features.publisher;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Transactional(readOnly = true)
    public Publisher findById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
    }

    @Transactional(readOnly = true)
    public PublisherResponse findDTOById(Long id) {
        return publisherMapper.toPublisherResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<PublisherResponse> findAll(Pageable pageable) {
        return publisherRepository.findAll(pageable).map(publisherMapper::toPublisherResponse);
    }

    @Transactional
    public PublisherResponse save(PublisherResponse publisherRequest) {
        Publisher transientPublisher = publisherMapper.toPublisher(publisherRequest);
        return publisherMapper.toPublisherResponse(publisherRepository.save(transientPublisher));
    }

    @Transactional
    public PublisherResponse update(Long id, PublisherRequest publisherRequest) {
        Publisher currentPublisher = findById(id);
        return publisherMapper.toPublisherResponse(publisherMapper.toExistingPublisher(publisherRequest, currentPublisher));
    }

    @Transactional
    public void delete(Long id) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Publisher.class, id);
        }
    }
}
