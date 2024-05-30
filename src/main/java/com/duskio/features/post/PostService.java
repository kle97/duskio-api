package com.duskio.features.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    public Optional<Post> findById(int postId) {
        return postDao.findById(postId);
    }

    public List<Post> findAll() {
        return postDao.findAll();
    }

    public List<Post> findAllWithImages() {
        return postDao.findAllWithImages();
    }

    public Page<Post> findAllWithImagesPageable(Pageable pageable) {
        return postDao.findAllWithImagesPageable(pageable);
    }

    public int save(String title, String description, LocalDateTime dateTime, int categoryId) {
        return postDao.save(title, description, dateTime, categoryId);
    }

    public void delete(int postId) {
        postDao.deleteById(postId);
    }
}
