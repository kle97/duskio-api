package com.duskio.features.profile;

import com.duskio.common.jsonview.BaseView;
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
@RequestMapping(PUBLIC_API_PATH + "profiles")
@Tag(name = "profile", description = "Profile API")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    @Operation(summary = "Find profile by id")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<ProfileEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(profileService.findEntityById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of profile")
    @JsonView(BaseView.Public.class)
    public ResponseEntity<PagedModel<ProfileResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(profileService.findAll(pageable)));
    }
}
