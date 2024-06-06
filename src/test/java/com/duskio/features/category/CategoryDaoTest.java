package com.duskio.features.category;

import com.duskio.common.BaseDaoTest;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.features.post.PostDao;
import com.duskio.features.post.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;

import java.util.List;

@Slf4j
public class CategoryDaoTest extends BaseDaoTest {
    
    private final CategoryDao categoryDao = getJdbi().onDemand(CategoryDao.class);

    @Test
    public void testFindById() {
        categoryDao.save(new CategoryRequest("New"));
        CategoryResponse expectedResponse = new CategoryResponse(1, "New", List.of());
        softAssert().as("findById").assertThat(categoryDao.findById(1)).hasValue(expectedResponse);
    }

    @Test
    public void testFindByIdWithOneToMany() {
        categoryDao.save(new CategoryRequest("New"));
        PostDao postDao = getJdbi().onDemand(PostDao.class);
        
        List.of(new PostRequest("Post", "Description", 1), new PostRequest("Post", "Description", 1),
                new PostRequest("Post", "Description", 1), new PostRequest("Post", "Description", 1))
            .forEach(postDao::save);

        softAssert().as("FindById category with posts")
                    .assertThat(categoryDao.findByIdWithPosts(1))
                    .get()
                    .satisfies(r -> softAssert().as("response").assertThat(r.categoryId()).isEqualTo(1),
                               r -> softAssert().as("response").assertThat(r.name()).isEqualTo("New"),
                               r -> softAssert().as("response").assertThat(r.posts()).hasSize(4));
    }

    @Test
    public void testFindAll() {
        List.of(new CategoryRequest("Category 1"), new CategoryRequest("Category 2"), new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        
        softAssert().as("Content")
                    .assertThat(categoryDao.findAll())
                    .hasSize(3)
                    .containsExactly(new CategoryResponse(1, "Category 1", List.of()),
                                     new CategoryResponse(2, "Category 2", List.of()),
                                     new CategoryResponse(3, "Category 3", List.of()));
    }

    @Test
    public void testFindPage() {
        List.of(new CategoryRequest("Category 1"), new CategoryRequest("Category 2"), new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        
        PagedModel<CategoryResponse> result = categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "category_id"));
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 0, 3, 2));
        
        softAssert().as("Content size")
                    .assertThat(result.getContent())
                    .hasSize(2)
                    .containsExactly(new CategoryResponse(3, "Category 3", List.of()), 
                                     new CategoryResponse(2, "Category 2", List.of()));

        result = categoryDao.findPage(PageRequest.of(1, 2, Sort.Direction.DESC, "category_id"));
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 1, 3, 2));
        
        softAssert().as("Content size")
                    .assertThat(result.getContent())
                    .hasSize(1)
                    .containsExactly(new CategoryResponse(1, "Category 1", List.of()));
    }

    @Test
    public void testFindScroll() {
        List.of(new CategoryRequest("Category 1"), new CategoryRequest("Category 2"), new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        
        ScrollResponse<CategoryResponse> result = categoryDao.findScroll(new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD, 
                                                                                           2, List.of("category_id", "desc")));
        softAssert().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softAssert().as("Size").assertThat(result.getSize()).isEqualTo(2);
        softAssert().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softAssert().as("Next").assertThat(result.isNext()).isEqualTo(true);

        softAssert().as("Content size")
                    .assertThat(result.getContent())
                    .hasSize(2)
                    .containsExactly(new CategoryResponse(3, "Category 3", List.of()), 
                                     new CategoryResponse(2, "Category 2", List.of()));
        
        result = categoryDao.findScroll(new ScrollRequest(List.of(2), ScrollPosition.Direction.FORWARD, 
                                                          2, List.of("category_id", "desc")));
        softAssert().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softAssert().as("Size").assertThat(result.getSize()).isEqualTo(1);
        softAssert().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softAssert().as("Next").assertThat(result.isNext()).isEqualTo(false);

        softAssert().as("Content size")
                    .assertThat(result.getContent())
                    .hasSize(1)
                    .containsExactly(new CategoryResponse(1, "Category 1", List.of()));
    }

    @Test
    public void testCreate() {
        softAssert().as("Create new category").assertThat(categoryDao.save(new CategoryRequest("New"))).isEqualTo(1);
        softAssert().as("New category").assertThat(categoryDao.findById(1)).hasValue(new CategoryResponse(1, "New", List.of()));
    }

    @Test
    public void testUpdate() {
        categoryDao.save(new CategoryRequest("New"));
        softAssert().as("Update").assertThat(categoryDao.update(1, new CategoryRequest("Updated"))).isEqualTo(true);
        softAssert().as("Updated").assertThat(categoryDao.findById(1)).hasValue(new CategoryResponse(1, "Updated", List.of()));
    }

    @Test
    public void testDelete() {
        categoryDao.save(new CategoryRequest("Deleting"));
        softAssert().as("Delete category").assertThat(categoryDao.deleteById(1)).isEqualTo(1);
        softAssert().as("Find deleted one empty").assertThat(categoryDao.findById(1)).isEmpty();
    }

}
