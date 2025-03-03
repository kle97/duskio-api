package com.duskio.features.rating;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingResponse toRatingResponse(Rating rating);
    
    Rating toRating(RatingRequest ratingRequest);
    
    Rating toExistingRating(RatingRequest ratingRequest, @MappingTarget Rating rating);
}
