package com.duskio.features.rating;

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
@RequestMapping(ADMIN_API_PATH + "ratings")
@Tag(name = "rating-admin", description = "Rating Admin API")
public class RatingAdminController {
    
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

    @PostMapping("")
    @Operation(summary = "Save new rating")
    public ResponseEntity<RatingResponse> save(@RequestBody @Validated RatingRequest ratingRequest) {
        var response = ratingService.save(ratingRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rating")
    public ResponseEntity<RatingResponse> update(@PathVariable Long id, 
                                                 @RequestBody @Validated RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingService.update(id, ratingRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rating")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
