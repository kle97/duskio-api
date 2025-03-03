package com.duskio.features.authorlink;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.duskio.common.constant.Constant.ADMIN_API_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_API_PATH + "author-links")
@Tag(name = "author-link-admin", description = "Author link Admin API")
public class AuthorLinkAdminController {
    
    private final AuthorLinkService authorLinkService;

    @GetMapping("/entity/{id}")
    @Operation(summary = "Find author link entity by id")
    public ResponseEntity<AuthorLink> findEntityById(@PathVariable Long id) {
        return ResponseEntity.ok().body(authorLinkService.findById(id));
    }

    @PostMapping("")
    @Operation(summary = "Save new author link")
    public ResponseEntity<AuthorLinkResponse> save(@RequestBody @Validated AuthorLinkRequest authorLinkRequest) {
        var response = authorLinkService.save(authorLinkRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update author link")
    public ResponseEntity<AuthorLinkResponse> update(@PathVariable Long id, 
                                                     @RequestBody @Validated AuthorLinkRequest authorLinkRequest) {
        return ResponseEntity.ok(authorLinkService.update(id, authorLinkRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author link")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorLinkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
