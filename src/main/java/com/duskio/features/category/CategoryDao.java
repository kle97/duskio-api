package com.duskio.features.category;

import com.duskio.features.post.PostDto;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@JdbiRepository
public interface CategoryDao {

    @SqlQuery("select * from category where category_id=?")
    Optional<Category> findById(int categoryId);

    @SqlQuery("select * from category")
    List<Category> findAll();
    
    @SqlQuery("select * from category left join post using (category_id)")
    @UseRowReducer(CategoryReducer.class)
    List<Category> findAllWithPosts();

    @SqlUpdate("insert into category (name) values (?)")
    @GetGeneratedKeys("id")
    int save(String name);

    @SqlUpdate("delete from category where category_id=?")
    void deleteById(int categoryId);
    
    class CategoryReducer implements LinkedHashMapRowReducer<Integer, Category> {
        @Override
        public void accumulate(Map<Integer, Category> container, RowView rowView) {
            Category category = container.computeIfAbsent(rowView.getColumn("category_id", Integer.class), 
                                                          id -> rowView.getRow(Category.class));
            if (rowView.getColumn("post_id", Integer.class) != null) {
                category.posts().add(rowView.getRow(PostDto.class));
            }
        }
    }
}
