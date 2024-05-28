package com.duskio.features.post;

import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JdbiRepository
public interface PostDao {

    @SqlQuery("SELECT ID, TITLE, DESCRIPTION, DATETIME, CATEGORY_ID FROM POST")
    List<Post> getAll();
}
