package com.duskio.features.post;

import com.duskio.common.PageableHelper;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.features.postimage.PostImageResponse;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@JdbiRepository
public interface PostDao {

    @SqlQuery("select * from post join category using (category_id) where post_id=?")
    Optional<PostResponse> findById(int postId);

    @SqlQuery("select * from post join category using (category_id) join post_image using (post_id) where post_id=?")
    @UseRowReducer(PostReducer.class)
    Optional<PostResponse> findByIdWithImages(int postId);
    
    @SqlQuery("select * from post left join category using (category_id)")
    List<PostResponse> findAll();

    @SqlQuery("select * from post left join category using (category_id) <queryString>")
    List<PostResponse> findPage(@Define String queryString);

    @SqlQuery("select * from post left join category using (category_id) <queryString>")
    List<PostResponse> findScroll(@Define String queryString);

    @SqlQuery("select count(*) from post")
    int count();

    default PagedModel<PostResponse> findPage(Pageable pageable) {
        return new PagedModel<>(new PageImpl<>(findPage(PageableHelper.getQueryString(pageable)), pageable, count()));
    }

    default ScrollResponse<PostResponse> findScroll(ScrollRequest scrollRequest) {
        return new ScrollResponse<>(findScroll(PageableHelper.getQueryString(scrollRequest)), scrollRequest.limit());
    }
    
    @SqlUpdate("insert into post (title, description, category_id) values (:title, :description, :categoryId)")
    @GetGeneratedKeys
    int save(@BindMethods PostRequest postRequest);

    @SqlUpdate("update post set title=:title, description=:description where post_id=?")
    boolean update(int postId, @BindMethods PostRequest postRequest);

    @SqlUpdate("delete from post where post_id=?")
    int deleteById(int postId);

    class PostReducer implements LinkedHashMapRowReducer<Integer, PostResponse> {
        @Override
        public void accumulate(Map<Integer, PostResponse> container, RowView rowView) {
            PostResponse post = container.computeIfAbsent(rowView.getColumn("post_id", Integer.class),
                                                  id -> rowView.getRow(PostResponse.class));
            if (rowView.getColumn("post_image_id", Integer.class) != null) {
                post.images().add(rowView.getRow(PostImageResponse.class));
            }
        }
    }
}
