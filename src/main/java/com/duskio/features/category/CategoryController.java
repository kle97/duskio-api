package com.duskio.features.category;

import com.duskio.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.duskio.common.constant.Constant.PUBLIC_API_PATH;

@Controller
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_PATH + "categories")
@Tag(name = "category", description = "Category API")
public class CategoryController {

    private final CategoryDao categoryDao;

    @GetMapping("/{id}")
    @Operation(summary = "Find category by id")
    public ResponseEntity<CategoryDto> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(categoryDao.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @GetMapping("")
    @Operation(summary = "Find all instances of category")
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.ok().body(categoryDao.findAll());
    }
}
