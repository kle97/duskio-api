package com.duskio.features.authorwork;

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
@RequestMapping(ADMIN_API_PATH + "author-works")
@Tag(name = "author-work-admin", description = "Author-Work Admin API")
public class AuthorWorkAdminController {
    
    private final AuthorWorkService authorWorkService;

    @GetMapping("/{authorId}/{workId}")
    @Operation(summary = "Find author-work by id")
    public ResponseEntity<AuthorWorkResponse> findById(@PathVariable Long authorId, @PathVariable Long workId) {
        return ResponseEntity.ok().body(authorWorkService.findEntityById(authorId, workId));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of author-work")
    public ResponseEntity<PagedModel<AuthorWorkResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(authorWorkService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new author-work")
    public ResponseEntity<AuthorWorkResponse> save(@RequestBody @Validated AuthorWorkRequest workAuthorRequest) {
        var response = authorWorkService.save(workAuthorRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id1}/{id2}")
                                                  .buildAndExpand(response.getAuthorId(), response.getWorkId())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{authorId}/{workId}")
    @Operation(summary = "Update author-work")
    public ResponseEntity<AuthorWorkResponse> update(@PathVariable Long authorId, @PathVariable Long workId,
                                                     @RequestBody @Validated AuthorWorkRequest workAuthorRequest) {
        return ResponseEntity.ok(authorWorkService.update(authorId, workId, workAuthorRequest));
    }

    @DeleteMapping("/{authorId}/{workId}")
    @Operation(summary = "Delete author-work")
    public ResponseEntity<Void> delete(@PathVariable Long authorId, @PathVariable Long workId) {
        authorWorkService.delete(authorId, workId);
        return ResponseEntity.noContent().build();
    }
}
