package com.duskio.features.review;

public record ReviewRequest(Long profileId, Long editionId, Integer score, String review) {
}
