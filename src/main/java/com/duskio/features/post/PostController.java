package com.duskio.features.post;

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
@RequestMapping(API_PATH +"posts")
@Tag(name = "post", description = "Post API")
public class PostController {

    private final PostDao postDao;

    @GetMapping("/{id}")
    @Operation(summary = "Find post instance by id")
    public ResponseEntity<PostResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(postDao.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @GetMapping("/{id}/images")
    @Operation(summary = "Find post instance by id with images")
    public ResponseEntity<PostResponse> findByIdWithImages(@PathVariable int id) {
        return ResponseEntity.ok().body(postDao.findByIdWithImages(id).orElseThrow(ResourceNotFoundException::new));
    }
    
    @GetMapping("")
    @Operation(summary = "Find all instances of post")
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok().body(postDao.findAll());
    }

    @GetMapping("/page")
    @Operation(summary = "Find instances of post with offset pagination")
    public ResponseEntity<PagedModel<PostResponse>> findPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(postDao.findPage(pageable));
    }

    @GetMapping("/scroll")
    @Operation(summary = "Find instances of post with keyset pagination")
    public ResponseEntity<ScrollResponse<PostResponse>> findScroll(@ParameterObject ScrollRequest scrollRequest) {
        return ResponseEntity.ok().body(postDao.findScroll(scrollRequest));
    }

    @PostMapping("")
    @Operation(summary = "Create new post")
    public ResponseEntity<Integer> save(@RequestBody @Validated PostRequest postRequest) {
        int id = postDao.save(postRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                                                  .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update post")
    public ResponseEntity<Boolean> update(@PathVariable int id, @RequestBody @Validated PostRequest postRequest) {
        return ResponseEntity.ok(postDao.update(id, postRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post")
    public ResponseEntity<Integer> delete(@PathVariable int id) {
        return ResponseEntity.ok(postDao.deleteById(id));
    }
}
