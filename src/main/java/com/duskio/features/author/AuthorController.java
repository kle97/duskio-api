package com.duskio.features.author;

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
@RequestMapping(PUBLIC_API_PATH + "authors")
@Tag(name = "author", description = "Author API")
public class AuthorController {
    
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
}
