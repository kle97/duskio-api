package com.duskio.features.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "post", description = "post API")
public class PostController {

    private final PostService postService;

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
}