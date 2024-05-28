package com.duskio.features.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryDao categoryDao;
    
    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
