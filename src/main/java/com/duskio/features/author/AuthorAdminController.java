package com.duskio.features.author;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.duskio.common.constant.Constant.ADMIN_API_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_API_PATH + "authors")
@Tag(name = "author-admin", description = "Author admin API")
public class AuthorAdminController {
    
    private final AuthorRepository authorRepository;

    @GetMapping("/page")
    @Operation(summary = "Find instances of author with offset pagination")
    public ResponseEntity<PagedModel<Author>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(authorRepository.findAll(pageable)));
    }
}
