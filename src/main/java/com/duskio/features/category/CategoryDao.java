package com.duskio.features.category;

import com.duskio.common.PageableHelper;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.features.post.PostResponse;
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
public interface CategoryDao {

    @SqlQuery("select * from category where category_id=?")
    Optional<CategoryResponse> findById(int id);

    @SqlQuery("select * from category join post using (category_id) where category.category_id=?")
    @UseRowReducer(CategoryReducer.class)
    Optional<CategoryResponse> findByIdWithPosts(int id);

    @SqlQuery("select * from category")
    List<CategoryResponse> findAll();

    @SqlQuery("select * from category <queryString>")
    List<CategoryResponse> findPage(@Define String queryString);

    @SqlQuery("select * from category <queryString>")
    List<CategoryResponse> findScroll(@Define String queryString);

    @SqlQuery("select count(*) from category")
    int count();

    default PagedModel<CategoryResponse> findPage(Pageable pageable) {
        return new PagedModel<>(new PageImpl<>(findPage(PageableHelper.getQueryString(pageable)), pageable, count()));
    }

    default ScrollResponse<CategoryResponse> findScroll(ScrollRequest scrollRequest) {
        return new ScrollResponse<>(findScroll(PageableHelper.getQueryString(scrollRequest)), scrollRequest.limit());
    }

    @SqlUpdate("insert into category (name) values (:name)")
    @GetGeneratedKeys
    int save(@BindMethods CategoryRequest categoryRequest);

    @SqlUpdate("update category set name=:name where category_id=:id")
    boolean update(int id, @BindMethods CategoryRequest categoryRequest);

    @SqlUpdate("delete from category where category_id=?")
    int deleteById(int id);
    
    class CategoryReducer implements LinkedHashMapRowReducer<Integer, CategoryResponse> {
        @Override
        public void accumulate(Map<Integer, CategoryResponse> container, RowView rowView) {
            CategoryResponse category = container.computeIfAbsent(rowView.getColumn("category_id", Integer.class), 
                                                          id -> rowView.getRow(CategoryResponse.class));
            if (rowView.getColumn("post_id", Integer.class) != null) {
                category.posts().add(rowView.getRow(PostResponse.class));
            }
        }
    }
}
