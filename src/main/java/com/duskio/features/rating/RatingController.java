package com.duskio.features.rating;

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
@RequestMapping(PUBLIC_API_PATH + "ratings")
@Tag(name = "rating", description = "Rating API")
public class RatingController {
    
    private final RatingService ratingService;

    @GetMapping("/{id}")
    @Operation(summary = "Find rating by id")
    public ResponseEntity<RatingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ratingService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of rating")
    public ResponseEntity<PagedModel<RatingResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(ratingService.findAll(pageable)));
    }
}
