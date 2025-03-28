package com.duskio.features.edition;

import com.duskio.common.jsonview.BaseView;
import com.duskio.features.edition.dto.EditionResponse;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping(PUBLIC_API_PATH + "editions")
@Tag(name = "edition", description = "Edition API")
public class EditionController {
    
    private final EditionService editionService;

    @GetMapping("/{id}")
    @Operation(summary = "Find edition by id")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<EditionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(editionService.findEntityById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of edition")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<PagedModel<EditionResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(editionService.findAll(pageable)));
    }
}
