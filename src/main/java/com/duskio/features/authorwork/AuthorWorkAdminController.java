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

    @GetMapping("/{workId}/{authorId}")
    @Operation(summary = "Find author-work by id")
    public ResponseEntity<AuthorWorkResponse> findById(@PathVariable Long workId, @PathVariable Long authorId) {
        return ResponseEntity.ok().body(authorWorkService.findDTOById(workId, authorId));
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
                                                  .buildAndExpand(response.workId(), response.authorId())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{workId}/{authorId}")
    @Operation(summary = "Update author-work")
    public ResponseEntity<AuthorWorkResponse> update(@PathVariable Long workId, @PathVariable Long authorId,
                                                     @RequestBody @Validated AuthorWorkRequest workAuthorRequest) {
        return ResponseEntity.ok(authorWorkService.update(workId, authorId, workAuthorRequest));
    }

    @DeleteMapping("/{workId}/{authorId}")
    @Operation(summary = "Delete author-work")
    public ResponseEntity<Void> delete(@PathVariable Long workId, @PathVariable Long authorId) {
        authorWorkService.delete(workId, authorId);
        return ResponseEntity.noContent().build();
    }
}
