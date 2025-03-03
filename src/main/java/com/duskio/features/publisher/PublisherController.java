package com.duskio.features.publisher;

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
@RequestMapping(PUBLIC_API_PATH + "publishers")
@Tag(name = "publisher", description = "Publisher API")
public class PublisherController {
    
    private final PublisherService publisherService;

    @GetMapping("/{id}")
    @Operation(summary = "Find publisher by id")
    public ResponseEntity<PublisherResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(publisherService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of publisher")
    public ResponseEntity<PagedModel<PublisherResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(publisherService.findAll(pageable)));
    }
}
