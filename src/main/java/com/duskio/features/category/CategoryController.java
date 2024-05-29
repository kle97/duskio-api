package com.duskio.features.category;

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
@RequestMapping("/api/v1/categories")
@Tag(name = "category", description = "category API")
public class CategoryController {
    
    private final CategoryService categoryService;
    
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
}
