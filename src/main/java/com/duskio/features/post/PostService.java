package com.duskio.features.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    public List<Post> getAll() {
        return postDao.getAll();
    }
}
