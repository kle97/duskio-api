package com.duskio.features.post;

import com.duskio.common.KeysetPageRequest;
import com.duskio.common.KeysetPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "post", description = "post API")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    @Operation(summary = "Find post instance by id")
    public ResponseEntity<Post> findById(@PathVariable int postId) {
        return ResponseEntity.ok().body(postService.findById(postId));
    }
    
    @GetMapping("")
    @Operation(summary = "Find all instances of post")
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok().body(postService.findAll());
    }

    @GetMapping("/images")
    @Operation(summary = "Find all instances of post with images")
    public ResponseEntity<List<Post>> findAllWithPosts() {
        return ResponseEntity.ok().body(postService.findAllWithImages());
    }

    @GetMapping("/pages")
    @Operation(summary = "Find instances of post with offset pagination")
    public ResponseEntity<Page<Post>> findPostInstancesAsPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(postService.findPostInstancesAsPage(pageable));
    }

    @GetMapping("/scroll")
    @Operation(summary = "Find instances of post with keyset pagination")
    public ResponseEntity<KeysetPageResponse<Post>> findPostInstancesAsKeysetPage(@ParameterObject KeysetPageRequest keysetPageRequest) {
        return ResponseEntity.ok().body(postService.findPostInstancesAsKeysetPage(keysetPageRequest));
    }
}
