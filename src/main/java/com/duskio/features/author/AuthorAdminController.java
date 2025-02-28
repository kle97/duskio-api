package com.duskio.features.author;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.duskio.common.constant.Constant.PUBLIC_API_PATH;

@Controller
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_PATH + "authors")
@Tag(name = "author-admin", description = "Author admin API")
public class AuthorAdminController {
    
    private final AuthorRepository authorRepository;

    @GetMapping("/page")
    @Operation(summary = "Find instances of author with offset pagination")
    public ResponseEntity<PagedModel<Author>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(authorRepository.findAll(pageable)));
    }
}
