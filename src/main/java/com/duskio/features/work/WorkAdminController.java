package com.duskio.features.work;

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
@RequestMapping(ADMIN_API_PATH + "works")
@Tag(name = "work-admin", description = "Work Admin API")
public class WorkAdminController {
    
    private final WorkService workService;

    @GetMapping("/{id}")
    @Operation(summary = "Find work by id")
    public ResponseEntity<WorkResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(workService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of work")
    public ResponseEntity<PagedModel<WorkPageResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(workService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new work")
    public ResponseEntity<WorkSimpleResponse> save(@RequestBody @Validated WorkRequest workRequest) {
        var response = workService.save(workRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update work")
    public ResponseEntity<WorkSimpleResponse> update(@PathVariable Long id, @RequestBody @Validated WorkRequest workRequest) {
        return ResponseEntity.ok(workService.update(id, workRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete work")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
