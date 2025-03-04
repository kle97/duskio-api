package com.duskio.features.edition;

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
@RequestMapping(ADMIN_API_PATH + "editions")
@Tag(name = "edition-admin", description = "Edition Admin API")
public class EditionAdminController {

    private final EditionService editionService;

    @GetMapping("/{id}")
    @Operation(summary = "Find edition by id")
    public ResponseEntity<EditionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(editionService.findDTOById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of edition")
    public ResponseEntity<PagedModel<EditionResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(editionService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new edition")
    public ResponseEntity<EditionResponse> save(@RequestBody @Validated
                                                EditionRequest editionRequest) {
        var response = editionService.save(editionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                                  .path("/{id}")
                                                  .buildAndExpand(response.id())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update edition")
    public ResponseEntity<EditionResponse> update(@PathVariable Long id, @RequestBody @Validated
    EditionRequest editionRequest) {
        return ResponseEntity.ok(editionService.update(id, editionRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete edition")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        editionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
