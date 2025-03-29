package com.duskio.features.profile;

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
@RequestMapping(ADMIN_API_PATH + "profiles")
@Tag(name = "profile-admin", description = "Profile Admin API")
public class ProfileAdminController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    @Operation(summary = "Find profile by id")
    public ResponseEntity<ProfileEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(profileService.findEntityById(id));
    }

    @GetMapping("")
    @Operation(summary = "Find pages of profile")
    public ResponseEntity<PagedModel<ProfileResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(profileService.findAll(pageable)));
    }

    @PostMapping("")
    @Operation(summary = "Save new profile")
    public ResponseEntity<ProfileResponse> save(@RequestBody @Validated ProfileResponse profileRequest) {
        var response = profileService.save(profileRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update profile")
    public ResponseEntity<ProfileResponse> update(@PathVariable Long id,
                                                  @RequestBody @Validated ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.update(id, profileRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete profile")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        profileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
