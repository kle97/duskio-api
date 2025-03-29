package com.duskio.features.review;

import com.duskio.common.exception.ResourceNotFoundException;
import com.duskio.features.edition.Edition;
import com.duskio.features.edition.EditionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EditionService editionService;
    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public Review findById(Long profileId, Long editionId) {
        return reviewRepository.findById(new ReviewId(profileId, editionId))
                               .orElseThrow(() -> new ResourceNotFoundException(Review.class, Long.class, profileId,
                                                                                Long.class, editionId));
    }

    @Transactional(readOnly = true)
    public ReviewEntityResponse findEntityById(Long profileId, Long editionId) {
        return reviewMapper.toReviewEntityResponse(reviewRepository.findEntityById(new ReviewId(profileId, editionId))
                                                                   .orElseThrow(() -> new ResourceNotFoundException(Review.class, Long.class, profileId,
                                                                                                                    Long.class, editionId)));
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toReviewResponse);
    }

    @Transactional
    public ReviewResponse save(ReviewRequest reviewRequest) {
        Review transientReview = reviewMapper.toReview(reviewRequest);
        Edition edition = editionService.findById(transientReview.getId().getEditionId());
        double totalRating = edition.getAverageRating() * edition.getRatingCount();
        int newRatingCount = edition.getRatingCount() + 1;
        double newAverageRating = (totalRating + transientReview.getScore()) / newRatingCount;
        edition.setAverageRating(newAverageRating);
        edition.setRatingCount(newRatingCount);
        return reviewMapper.toReviewResponse(reviewRepository.save(transientReview));
    }

    @Transactional
    public ReviewResponse update(Long profileId, Long editionId, ReviewRequest reviewRequest) {
        Review currentReview = findById(profileId, editionId);
        Edition edition = editionService.findById(currentReview.getId().getEditionId());
        double totalRating = edition.getAverageRating() * edition.getRatingCount();
        double newAverageRating = (totalRating - currentReview.getScore() + reviewRequest.score()) / edition.getRatingCount();
        edition.setAverageRating(newAverageRating);
        return reviewMapper.toReviewResponse(reviewMapper.toExistingReview(reviewRequest, currentReview));
    }

    @Transactional
    public void delete(Long profileId, Long editionId) {
        ReviewId reviewId = new ReviewId(profileId, editionId);
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new ResourceNotFoundException(Review.class, Long.class, profileId, Long.class, editionId);
        }
    }
}
