package com.duskio.features.post;

import com.duskio.common.KeysetPageRequest;
import com.duskio.common.KeysetPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    public Post findById(int postId) {
        return postDao.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Post> findAll() {
        return postDao.findOffsetPage();
    }

    public List<Post> findAllWithImages() {
        return postDao.findAllWithImages();
    }

    public Page<Post> findPostInstancesAsPage(Pageable pageable) {
        return postDao.findPostInstancesAsPage(pageable);
    }

    public KeysetPageResponse<Post> findPostInstancesAsKeysetPage(KeysetPageRequest keysetPageRequest) {
        return postDao.findPostInstancesAsKeysetPage(keysetPageRequest);
    }

    public int save(String title, String description, int categoryId) {
        return postDao.save(title, description, LocalDateTime.now(), categoryId);
    }

    public void deleteById(int postId) {
        postDao.deleteById(postId);
    }
}
