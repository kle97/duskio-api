package com.duskio.features.publisher;

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
@RequestMapping(ADMIN_API_PATH + "publishers")
@Tag(name = "publisher-admin", description = "Publisher Admin API")
public class PublisherAdminController {
    
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

    @PostMapping("")
    @Operation(summary = "Save new publisher")
    public ResponseEntity<PublisherResponse> save(@RequestBody @Validated PublisherResponse publisherRequest) {
        var response = publisherService.save(publisherRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update publisher")
    public ResponseEntity<PublisherResponse> update(@PathVariable Long id, 
                                                    @RequestBody @Validated PublisherRequest publisherRequest) {
        return ResponseEntity.ok(publisherService.update(id, publisherRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publisher")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
