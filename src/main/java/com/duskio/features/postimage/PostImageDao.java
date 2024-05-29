package com.duskio.features.postimage;

import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@JdbiRepository
public interface PostImageDao {

    @SqlQuery("select * from post_image left join post using (post_id)")
    List<PostImage> findAll();
}
