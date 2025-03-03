package com.duskio.features.subject;

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
@RequestMapping(PUBLIC_API_PATH + "subjects")
@Tag(name = "subject", description = "Subject API")
public class SubjectController {
    
    private final SubjectService subjectService;

    @GetMapping("/{id}")
    @Operation(summary = "Find subject by id")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(subjectService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of subject")
    public ResponseEntity<PagedModel<SubjectResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(subjectService.findAll(pageable)));
    }
}
