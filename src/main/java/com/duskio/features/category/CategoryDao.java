package com.duskio.features.category;

import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JdbiRepository
public interface CategoryDao {
    
    @SqlQuery("SELECT ID, NAME FROM CATEGORY")
    List<Category> getAll();
    
    
}
