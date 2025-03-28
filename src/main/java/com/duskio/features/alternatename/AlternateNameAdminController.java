package com.duskio.features.alternatename;

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
@RequestMapping(ADMIN_API_PATH + "alternate-names")
@Tag(name = "alternate-name-admin", description = "Alternate name Admin API")
public class AlternateNameAdminController {
    
    private final AlternateNameService alternateNameService;

    @GetMapping("/{id}")
    @Operation(summary = "Find alternate name by id")
    public ResponseEntity<AlternateNameEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(alternateNameService.findEntityById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of alternate name")
    public ResponseEntity<PagedModel<AlternateNameResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(alternateNameService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new alternate name")
    public ResponseEntity<AlternateNameResponse> save(@RequestBody @Validated AlternateNameRequest alternateNameRequest) {
        var response = alternateNameService.save(alternateNameRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update alternate name")
    public ResponseEntity<AlternateNameResponse> update(@PathVariable Long id, 
                                                        @RequestBody @Validated AlternateNameRequest alternateNameRequest) {
        return ResponseEntity.ok(alternateNameService.update(id, alternateNameRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete alternate name")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alternateNameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
