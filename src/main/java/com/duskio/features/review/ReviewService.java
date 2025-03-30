package com.duskio.features.review;

import com.duskio.common.exception.DuplicateResourceException;
import com.duskio.common.exception.InvalidRequestException;
import com.duskio.common.exception.ResourceNotFoundException;
import com.duskio.features.edition.Edition;
import com.duskio.features.edition.EditionService;
import com.duskio.features.profile.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j @RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EditionService editionService;
    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public Review findById(Long profileId, Long editionId) {
        return reviewRepository.findByProfileIdAndEditionId(profileId, editionId)
                               .orElseThrow(() -> new ResourceNotFoundException(Review.class, Profile.class, profileId, 
                                                                                Edition.class, editionId));
    }

    @Transactional(readOnly = true)
    public ReviewEntityResponse findEntityById(Long profileId, Long editionId) {
        Review entity = reviewRepository.findEntityByProfile_IdAndEdition_Id(profileId, editionId)
                                        .orElseThrow(() -> new ResourceNotFoundException(Review.class, Profile.class, profileId, 
                                                                                         Edition.class, editionId));
        return reviewMapper.toReviewEntityResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toReviewResponse);
    }

    @Transactional
    public ReviewResponse save(ReviewRequest reviewRequest) {
        if (reviewRepository.existsByProfile_IdAndEdition_Id(reviewRequest.profileId(), reviewRequest.editionId())) {
            throw new DuplicateResourceException(Review.class, Profile.class, reviewRequest.profileId(), 
                                                 Edition.class, reviewRequest.editionId());
        }
        Review transientReview = reviewMapper.toReview(reviewRequest);
        Edition edition = editionService.findById(reviewRequest.editionId());
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
        if (!Objects.equals(currentReview.getEditionId(), reviewRequest.editionId()) 
                || !Objects.equals(currentReview.getProfileId(), reviewRequest.profileId())) {
            throw new InvalidRequestException(Review.class, "Cannot update review's profile id and edition id.");
        }
        Edition edition = editionService.findById(reviewRequest.editionId());
        double totalRating = edition.getAverageRating() * edition.getRatingCount();
        double newAverageRating = (totalRating - currentReview.getScore() + reviewRequest.score()) / edition.getRatingCount();
        edition.setAverageRating(newAverageRating);
        return reviewMapper.toReviewResponse(reviewMapper.toExistingReview(reviewRequest, currentReview));
    }

    @Transactional
    public void delete(Long profileId, Long editionId) {
        if (reviewRepository.existsByProfile_IdAndEdition_Id(profileId, editionId)) {
            reviewRepository.deleteByProfile_IdAndEdition_Id(profileId, editionId);
        } else {
            throw new ResourceNotFoundException(Review.class, Profile.class, profileId, Edition.class, editionId);
        }
    }
}
