package com.duskio.features.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.duskio.common.constant.Constant.ADMIN_API_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_API_PATH + "reviews")
@Tag(name = "review-admin", description = "Review Admin API")
public class ReviewAdminController {
    
    private final ReviewService reviewService;

    @GetMapping("/{profileId}/{editionId}")
    @Operation(summary = "Find review by id")
    public ResponseEntity<ReviewEntityResponse> findById(@PathVariable Long profileId, @PathVariable Long editionId) {
        return ResponseEntity.ok().body(reviewService.findEntityById(profileId, editionId));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of review")
    public ResponseEntity<PagedModel<ReviewResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(reviewService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new review")
    public ResponseEntity<ReviewResponse> save(@RequestBody @Validated ReviewRequest reviewRequest) {
        var response = reviewService.save(reviewRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                                  .path("/{id1}/{id2}")
                                                  .buildAndExpand(response.getProfileId(), response.getEditionId())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{profileId}/{editionId}")
    @Operation(summary = "Update review")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long profileId, @PathVariable Long editionId,
                                                 @RequestBody @Validated ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.update(profileId, editionId, reviewRequest));
    }

    @DeleteMapping("/{profileId}/{editionId}")
    @Operation(summary = "Delete review")
    public ResponseEntity<Void> delete(@PathVariable Long profileId, @PathVariable Long editionId) {
        reviewService.delete(profileId, editionId);
        return ResponseEntity.noContent().build();
    }
}
