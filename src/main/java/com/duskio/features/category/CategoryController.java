package com.duskio.features.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "category", description = "category API")
public class CategoryController {
    
    private final CategoryService categoryService;

    @GetMapping("/{categoryId}")
    @Operation(summary = "Find category by id")
    public ResponseEntity<Category> findById(@PathVariable int categoryId) {
        return ResponseEntity.ok().body(categoryService.findById(categoryId));
    }
    
    @GetMapping("")
    @Operation(summary = "Find all instances of category")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @GetMapping("/posts")
    @Operation(summary = "Find all instances of category with posts")
    public ResponseEntity<List<Category>> findAllWithPosts() {
        return ResponseEntity.ok().body(categoryService.findAllWithPosts());
    }

    @PostMapping("")
    @Operation(summary = "Find category by id")
    public ResponseEntity<Integer> save(@RequestBody CategoryRequest categoryRequest) {
        int categoryId = categoryService.save(categoryRequest.name());
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{categoryId}")
                                                  .buildAndExpand(categoryId).toUri();
        return ResponseEntity.created(location).build();
    }
}
