package com.duskio.features.category;

import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.duskio.common.constant.Constant.ADMIN_API_PATH;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADMIN_API_PATH + "categories")
@Tag(name = "category-admin", description = "Category admin API")
public class CategoryAdminController {
    
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

    @GetMapping("/page")
    @Operation(summary = "Find instances of category with offset pagination")
    public ResponseEntity<PagedModel<CategoryDto>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(categoryDao.findPage(pageable));
    }

    @GetMapping("/scroll")
    @Operation(summary = "Find instances of category with keyset pagination")
    public ResponseEntity<ScrollResponse<CategoryDto>> findScroll(@ParameterObject ScrollRequest scrollRequest) {
        return ResponseEntity.ok().body(categoryDao.findScroll(scrollRequest));
    }

    @PostMapping("")
    @Operation(summary = "Create new category")
    public ResponseEntity<Integer> save(@RequestBody @Validated CategorySaveDto categorySaveDto) {
        int id = categoryDao.save(categorySaveDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                                                  .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<Boolean> update(@PathVariable int id, @RequestBody @Validated CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryDao.update(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<Integer> delete(@PathVariable int id) {
        return ResponseEntity.ok(categoryDao.deleteById(id));
    }
}
