package com.duskio.features.post;

import com.duskio.features.postimage.PostImageDto;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;
import java.util.Map;

@JdbiRepository
public interface PostDao extends SqlObject {

    @SqlQuery("select * from post left join category using (category_id)")
    List<Post> findAll();

    @SqlQuery("select * from post left join category using (category_id) left join post_image using (post_id)")
    @UseRowReducer(PostReducer.class)
    List<Post> findAllWithImages();

    class PostReducer implements LinkedHashMapRowReducer<Integer, Post> {
        @Override
        public void accumulate(Map<Integer, Post> container, RowView rowView) {
            Post post = container.computeIfAbsent(rowView.getColumn("post_id", Integer.class),
                                                  id -> rowView.getRow(Post.class));
            if (rowView.getColumn("post_image_id", Integer.class) != null) {
                post.images().add(rowView.getRow(PostImageDto.class));
            }
        }
    }
}
