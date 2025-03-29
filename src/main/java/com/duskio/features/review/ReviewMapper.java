package com.duskio.features.review;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewResponse toReviewResponse(Review review);

    ReviewEntityResponse toReviewEntityResponse(Review review);

    Review toReview(ReviewRequest reviewRequest);
    
    Review toExistingReview(ReviewRequest reviewRequest, @MappingTarget Review review);
}
