package com.duskio.features.work;

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
@RequestMapping(PUBLIC_API_PATH + "works")
@Tag(name = "work", description = "Work API")
public class WorkController {
    
    private final WorkService workService;

    @GetMapping("/{id}")
    @Operation(summary = "Find work by id")
    public ResponseEntity<WorkEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(workService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of work")
    public ResponseEntity<PagedModel<WorkResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(workService.findAll(pageable)));
    }
}
