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
import java.util.Optional;

@Slf4j
public class CategoryDaoTest extends BaseDaoTest {
    
    private final CategoryDao categoryDao = getJdbi().onDemand(CategoryDao.class);

    @Test
    public void testFindById() {
        log.info("Test findById");
        CategoryRequest request = new CategoryRequest("New");
        categoryDao.save(request);
        CategoryResponse expectedResponse = new CategoryResponse(1, "New", List.of());
        softAssert().as("New").assertThat(categoryDao.findById(1)).hasValue(expectedResponse);
    }

    @Test
    public void testFindByIdWithOneToMany() {
        log.info("Test findById with one to many");
        
        categoryDao.save(new CategoryRequest("New"));
        
        PostDao postDao = getJdbi().onDemand(PostDao.class);
        List.of(new PostRequest("Post", "Description", 1),
                new PostRequest("Post", "Description", 1),
                new PostRequest("Post", "Description", 1), 
                new PostRequest("Post", "Description", 1))
            .forEach(postDao::save);
        
        Optional<CategoryResponse> optional = categoryDao.findByIdWithPosts(1);
        softAssert().as("FindById category with posts").assertThat(optional).isPresent();
        if (optional.isPresent()) {
            CategoryResponse response = optional.get();
            softAssert().as("FindById category with posts").assertThat(response.categoryId()).isEqualTo(1);
            softAssert().as("FindById category with posts").assertThat(response.name()).isEqualTo("New");
            softAssert().as("FindById category with posts").assertThat(response.posts()).hasSize(4);
        }
    }

    @Test
    public void testFindAll() {
        log.info("Test findAll");
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"), 
                                                 new CategoryRequest("Category 2"), 
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);

        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(1, "Category 1", List.of()),
                                                           new CategoryResponse(2, "Category 2", List.of()), 
                                                           new CategoryResponse(3, "Category 3", List.of()));
        
        List<CategoryResponse> results = categoryDao.findAll();
        
        softAssert().as("Content size").assertThat(results).hasSize(3);
        softAssert().as("Content").assertThat(results).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testFindPage() {
        log.info("Test findPage");
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"),
                                                 new CategoryRequest("Category 2"),
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);
        
        PagedModel<CategoryResponse> result = categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "category_id"));
        
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(3, "Category 3", List.of()),
                                                           new CategoryResponse(2, "Category 2", List.of()));
        
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 0, 3, 2));
        
        softAssert().as("Content size").assertThat(result.getContent()).hasSize(2);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);

        result = categoryDao.findPage(PageRequest.of(1, 2, Sort.Direction.DESC, "category_id"));
        
        expectedResponses = List.of(new CategoryResponse(1, "Category 1", List.of()));
        
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 1, 3, 2));

        softAssert().as("Content size").assertThat(result.getContent()).hasSize(1);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testFindScroll() {
        log.info("Test findScroll");
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"),
                                                 new CategoryRequest("Category 2"),
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);
        
        ScrollResponse<CategoryResponse> result = categoryDao.findScroll(new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD, 
                                                                                           2, List.of("category_id", "desc")));
        
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(3, "Category 3", List.of()), 
                                                           new CategoryResponse(2, "Category 2", List.of()));

        softAssert().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softAssert().as("Size").assertThat(result.getSize()).isEqualTo(2);
        softAssert().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softAssert().as("Next").assertThat(result.isNext()).isEqualTo(true);

        softAssert().as("Content size").assertThat(result.getContent()).hasSize(2);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);
        
        result = categoryDao.findScroll(new ScrollRequest(List.of(2), ScrollPosition.Direction.FORWARD, 
                                                          2, List.of("category_id", "desc")));
        
        expectedResponses = List.of(new CategoryResponse(1, "Category 1", List.of()));

        softAssert().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softAssert().as("Size").assertThat(result.getSize()).isEqualTo(1);
        softAssert().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softAssert().as("Next").assertThat(result.isNext()).isEqualTo(false);

        softAssert().as("Content size").assertThat(result.getContent()).hasSize(1);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testCreate() {
        log.info("Test create");
        CategoryRequest request = new CategoryRequest("New");
        CategoryResponse expectedResponse = new CategoryResponse(1, "New", List.of());
        softAssert().as("Create new category").assertThat(categoryDao.save(request)).isEqualTo(1);
        softAssert().as("New category").assertThat(categoryDao.findById(1)).hasValue(expectedResponse);
    }

    @Test
    public void testUpdate() {
        log.info("Test update");
        CategoryRequest newRequest = new CategoryRequest("New");
        CategoryRequest updateRequest = new CategoryRequest("Updated");
        CategoryResponse expectedResponse = new CategoryResponse(1, "Updated", List.of());
        categoryDao.save(newRequest);
        
        softAssert().as("Update category").assertThat(categoryDao.update(1, updateRequest)).isEqualTo(true);
        softAssert().as("Updated category").assertThat(categoryDao.findById(1)).hasValue(expectedResponse);
    }

    @Test
    public void testDelete() {
        log.info("Test delete");
        CategoryRequest request = new CategoryRequest("Deleting");
        categoryDao.save(request);
        
        softAssert().as("Delete category").assertThat(categoryDao.deleteById(1)).isEqualTo(1);
        softAssert().as("Find deleted category empty").assertThat(categoryDao.findById(1)).isEmpty();
    }

}
