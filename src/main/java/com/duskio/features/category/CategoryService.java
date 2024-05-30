package com.duskio.features.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryDao categoryDao;
    
    public Category findById(int categoryId) {
        return categoryDao.findById(categoryId)
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public List<Category> findAllWithPosts() {
        return categoryDao.findAllWithPosts();
    }

    public int save(String name) {
        return categoryDao.save(name);
    }

    public void delete(int categoryId) {
        categoryDao.deleteById(categoryId);
    }
}
