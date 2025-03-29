package com.duskio.features.review;

import com.duskio.common.jsonview.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.duskio.common.constant.Constant.PUBLIC_API_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_PATH + "reviews")
@Tag(name = "review", description = "Review API")
public class ReviewController {
    
    private final ReviewService reviewService;

    @GetMapping("/{profileId}/{editionId}")
    @Operation(summary = "Find review by id")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<ReviewEntityResponse> findById(@PathVariable Long profileId, @PathVariable Long editionId) {
        return ResponseEntity.ok().body(reviewService.findEntityById(profileId, editionId));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of review")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<PagedModel<ReviewResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(reviewService.findAll(pageable)));
    }
}
