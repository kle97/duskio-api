package com.duskio.features.postimage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {
    
    private final PostImageDao postImageDao;

    public List<PostImage> findAll() {
        return postImageDao.findAll();
    }
}
