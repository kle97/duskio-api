package com.duskio.features.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    public List<Post> findAll() {
        return postDao.findAll();
    }

    public List<Post> findAllWithImages() {
        return postDao.findAllWithImages();
    }
}
