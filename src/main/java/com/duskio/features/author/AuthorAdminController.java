package com.duskio.features.author;

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
@RequestMapping(ADMIN_API_PATH + "authors")
@Tag(name = "author-admin", description = "Author Admin API")
public class AuthorAdminController {
    
    private final AuthorService authorService;

    @GetMapping("/{id}")
    @Operation(summary = "Find author by id")
    public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(authorService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of author")
    public ResponseEntity<PagedModel<AuthorResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(authorService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new author")
    public ResponseEntity<AuthorResponse> save(@RequestBody @Validated AuthorRequest authorRequest) {
        var response = authorService.save(authorRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update author")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id, @RequestBody @Validated AuthorRequest authorRequest) {
        return ResponseEntity.ok(authorService.update(id, authorRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
