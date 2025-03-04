package com.duskio.features.worksubject;

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
@RequestMapping(ADMIN_API_PATH + "work-subjects")
@Tag(name = "work-subject-admin", description = "Work-Subject Admin API")
public class WorkSubjectAdminController {
    
    private final WorkSubjectService workSubjectService;

    @GetMapping("/{workId}/{subjectId}")
    @Operation(summary = "Find work-subject by id")
    public ResponseEntity<WorkSubjectResponse> findById(@PathVariable Long workId, @PathVariable Long subjectId) {
        return ResponseEntity.ok().body(workSubjectService.findDTOById(workId, subjectId));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of work-subject")
    public ResponseEntity<PagedModel<WorkSubjectResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(workSubjectService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new work-subject")
    public ResponseEntity<WorkSubjectResponse> save(@RequestBody @Validated WorkSubjectRequest workSubjectRequest) {
        var response = workSubjectService.save(workSubjectRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id1}/{id2}")
                                                  .buildAndExpand(response.workId(), response.subjectId())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{workId}/{subjectId}")
    @Operation(summary = "Update work-subject")
    public ResponseEntity<WorkSubjectResponse> update(@PathVariable Long workId, @PathVariable Long subjectId,
                                                      @RequestBody @Validated WorkSubjectRequest workSubjectRequest) {
        return ResponseEntity.ok(workSubjectService.update(workId, subjectId, workSubjectRequest));
    }

    @DeleteMapping("/{workId}/{subjectId}")
    @Operation(summary = "Delete work-subject")
    public ResponseEntity<Void> delete(@PathVariable Long workId, @PathVariable Long subjectId) {
        workSubjectService.delete(workId, subjectId);
        return ResponseEntity.noContent().build();
    }
}
