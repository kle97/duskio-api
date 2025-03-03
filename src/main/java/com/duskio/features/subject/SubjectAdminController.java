package com.duskio.features.subject;

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
@RequestMapping(ADMIN_API_PATH + "subjects")
@Tag(name = "subject-admin", description = "Subject Admin API")
public class SubjectAdminController {
    
    private final SubjectService subjectService;

    @GetMapping("/{id}")
    @Operation(summary = "Find subject by id")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(subjectService.findDTOById(id));
    }

    @GetMapping("/entity/{id}")
    @Operation(summary = "Find subject entity by id")
    public ResponseEntity<Subject> findEntityById(@PathVariable Long id) {
        return ResponseEntity.ok().body(subjectService.findById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of subject")
    public ResponseEntity<PagedModel<SubjectResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(subjectService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new subject")
    public ResponseEntity<SubjectResponse> save(@RequestBody @Validated SubjectRequest subjectRequest) {
        var response = subjectService.save(subjectRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update subject")
    public ResponseEntity<SubjectResponse> update(@PathVariable Long id,
                                                  @RequestBody @Validated SubjectRequest subjectRequest) {
        return ResponseEntity.ok(subjectService.update(id, subjectRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete subject")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
