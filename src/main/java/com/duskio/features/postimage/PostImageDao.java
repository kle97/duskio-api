package com.duskio.features.postimage;

import com.duskio.common.PageableHelper;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.Optional;

@JdbiRepository
public interface PostImageDao {

    @SqlQuery("select * from post_image join post using (post_id) where post_id=?")
    Optional<PostImageResponse> findById(int postId);

    @SqlQuery("select * from post_image left join post using (post_id)")
    List<PostImageResponse> findAll();

    @SqlQuery("select * from post_image left join post using (post_id) <queryString>")
    List<PostImageResponse> findPage(@Define String queryString);

    @SqlQuery("select * from post_image left join post using (post_id) <queryString>")
    List<PostImageResponse> findScroll(@Define String queryString);

    @SqlQuery("select count(*) from post_image")
    int count();

    default PagedModel<PostImageResponse> findPage(Pageable pageable) {
        return new PagedModel<>(new PageImpl<>(findPage(PageableHelper.getQueryString(pageable)), pageable, count()));
    }

    default ScrollResponse<PostImageResponse> findScroll(ScrollRequest scrollRequest) {
        return new ScrollResponse<>(findScroll(PageableHelper.getQueryString(scrollRequest)), scrollRequest.limit());
    }

    @SqlUpdate("insert into post_image (image_link, post_id) values (:image_link, :post_id)")
    @GetGeneratedKeys
    int save(@BindMethods PostImageRequest postImageRequest);

    @SqlUpdate("update post_image set image_link=:image_link where post_image_id=?")
    boolean update(int postImageId, @BindMethods PostImageRequest postImageRequest);

    @SqlUpdate("delete from post_image where post_image_id=?")
    int deleteById(int postImageId);
}
