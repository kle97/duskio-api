package com.duskio.features.rating;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    @Transactional(readOnly = true)
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Rating.class, id));
    }

    @Transactional(readOnly = true)
    public RatingResponse findDTOById(Long id) {
        return ratingMapper.toRatingResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<RatingResponse> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable).map(ratingMapper::toRatingResponse);
    }

    @Transactional
    public RatingResponse save(RatingRequest ratingRequest) {
        Rating transientRating = ratingMapper.toRating(ratingRequest);
        return ratingMapper.toRatingResponse(ratingRepository.save(transientRating));
    }

    @Transactional
    public RatingResponse update(Long id, RatingRequest ratingRequest) {
        Rating currentRating = findById(id);
        return ratingMapper.toRatingResponse(ratingMapper.toExistingRating(ratingRequest, currentRating));
    }

    @Transactional
    public void delete(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Rating.class, id);
        }
    }
}
