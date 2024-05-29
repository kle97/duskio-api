package com.duskio.features.postimage;

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
@RequestMapping("/api/v1/post-images")
@Tag(name = "post-image", description = "post image API")
public class PostImageController {
    
    private final PostImageService postImageService;

    @GetMapping("")
    @Operation(summary = "Find all instances of post's image")
    public ResponseEntity<List<PostImage>> findAll() {
        return ResponseEntity.ok().body(postImageService.findAll());
    }
}
