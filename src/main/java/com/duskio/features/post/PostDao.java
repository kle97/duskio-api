package com.duskio.features.post;

import com.duskio.common.KeysetPageRequest;
import com.duskio.common.KeysetPageResponse;
import com.duskio.common.PageableHelper;
import com.duskio.features.postimage.PostImageDto;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JdbiRepository
public interface PostDao extends SqlObject {

    @SqlQuery("select * from post where post_id=?")
    Optional<Post> findById(int postId);
    
    @SqlQuery("select * from post left join category using (category_id)")
    List<Post> findOffsetPage();
    
    @SqlQuery("select * from post left join category using (category_id) left join post_image using (post_id)")
    @UseRowReducer(PostReducer.class)
    List<Post> findAllWithImages();

    @SqlQuery("select * from post left join category using (category_id) <queryString>")
    List<Post> findKeysetPage(@Define String queryString);

    @SqlQuery("select * from post left join category using (category_id) <queryString>")
    List<Post> findOffsetPage(@Define String queryString);

    @SqlQuery("select count(*) from post")
    int count();
    
    default Page<Post> findPostInstancesAsPage(Pageable pageable) {
        return new PageImpl<>(findOffsetPage(PageableHelper.getQueryString(pageable)), pageable, count());
    }

    default KeysetPageResponse<Post> findPostInstancesAsKeysetPage(KeysetPageRequest request) {
        return new KeysetPageResponse<>(findKeysetPage(request.getQueryString()), request.limit());
    }

    @SqlUpdate("insert into post (title, description, datetime, category_id) values (?, ?, ?, ?)")
    @GetGeneratedKeys("id")
    int save(String title, String description, LocalDateTime dateTime, int categoryId);

    @SqlUpdate("delete from post where post_id=?")
    void deleteById(int postId);

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
