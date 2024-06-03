package com.duskio.features.postimage;

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

import static com.duskio.common.constant.Constant.API_PATH;

@Controller
@RequiredArgsConstructor
@RequestMapping(API_PATH +"post-images")
@Tag(name = "post-image", description = "Post's images API")
public class PostImageController {
    
    private final PostImageDao postImageDao;

    @GetMapping("/{id}")
    @Operation(summary = "Find post's instance by id")
    public ResponseEntity<PostImageResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(postImageDao.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @GetMapping("")
    @Operation(summary = "Find all instances of post's image")
    public ResponseEntity<List<PostImageResponse>> findAll() {
        return ResponseEntity.ok().body(postImageDao.findAll());
    }

    @GetMapping("/page")
    @Operation(summary = "Find instances of post's image with offset pagination")
    public ResponseEntity<PagedModel<PostImageResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(postImageDao.findPage(pageable));
    }

    @GetMapping("/scroll")
    @Operation(summary = "Find instances of post's image with keyset pagination")
    public ResponseEntity<ScrollResponse<PostImageResponse>> findScroll(@ParameterObject ScrollRequest scrollRequest) {
        return ResponseEntity.ok().body(postImageDao.findScroll(scrollRequest));
    }

    @PostMapping("")
    @Operation(summary = "Create new post's image")
    public ResponseEntity<Integer> save(@RequestBody @Validated PostImageRequest postImageRequest) {
        int id = postImageDao.save(postImageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                                                  .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update post's image")
    public ResponseEntity<Boolean> update(@PathVariable int id, 
                                          @RequestBody @Validated PostImageRequest postImageRequest) {
        return ResponseEntity.ok(postImageDao.update(id, postImageRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post's image")
    public ResponseEntity<Integer> delete(@PathVariable int id) {
        return ResponseEntity.ok(postImageDao.deleteById(id));
    }
}
