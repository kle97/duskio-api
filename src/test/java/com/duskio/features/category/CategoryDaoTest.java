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
        CategoryResponse expectedResponse = new CategoryResponse(getNextId(), "New", List.of());
        softAssert().as("New").assertThat(categoryDao.findById(getId())).hasValue(expectedResponse);
    }

    @Test
    public void testFindByIdWithOneToMany() {
        log.info("Test findById with one to many");
        
        categoryDao.save(new CategoryRequest("New"));
        
        PostDao postDao = getJdbi().onDemand(PostDao.class);
        List.of(new PostRequest("Post", "Description", getNextId()),
                new PostRequest("Post", "Description", getId()),
                new PostRequest("Post", "Description", getId()), 
                new PostRequest("Post", "Description", getId()))
            .forEach(postDao::save);
        
        Optional<CategoryResponse> optional = categoryDao.findByIdWithPosts(getId());
        softAssert().as("FindById category with posts").assertThat(optional).isPresent();
        if (optional.isPresent()) {
            CategoryResponse response = optional.get();
            softAssert().as("FindById category with posts").assertThat(response.categoryId()).isEqualTo(getId());
            softAssert().as("FindById category with posts").assertThat(response.name()).isEqualTo("New");
            softAssert().as("FindById category with posts").assertThat(response.posts()).hasSize(4);
        }
    }

    @Test
    public void testFindAll() {
        log.info("Test findAll");
        List<Integer> ids = List.of(getNextId(), getNextId(), getNextId());
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"), 
                                                 new CategoryRequest("Category 2"), 
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);

        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(ids.get(0), "Category 1", List.of()),
                                                           new CategoryResponse(ids.get(1), "Category 2", List.of()), 
                                                           new CategoryResponse(ids.get(2), "Category 3", List.of()));
        
        List<CategoryResponse> results = categoryDao.findAll();
        
        softAssert().as("Content size").assertThat(results).hasSize(3);
        softAssert().as("Content").assertThat(results).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testFindPage() {
        log.info("Test findPage");
        List<Integer> ids = List.of(getNextId(), getNextId(), getNextId());
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"),
                                                 new CategoryRequest("Category 2"),
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);
        
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "category_id");
        PagedModel<CategoryResponse> result = categoryDao.findPage(pageRequest);
        
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(ids.get(2), "Category 3", List.of()),
                                                           new CategoryResponse(ids.get(1), "Category 2", List.of()));
        
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 0, 3, 2));
        
        softAssert().as("Content size").assertThat(result.getContent()).hasSize(2);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);

        pageRequest = PageRequest.of(1, 2, Sort.Direction.DESC, "category_id");
        result = categoryDao.findPage(pageRequest);
        
        expectedResponses = List.of(new CategoryResponse(ids.getFirst(), "Category 1", List.of()));
        
        softAssert().as("PageMetadata")
                    .assertThat(result.getMetadata())
                    .isEqualTo(new PagedModel.PageMetadata(2, 1, 3, 2));

        softAssert().as("Content size").assertThat(result.getContent()).hasSize(1);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testFindScroll() {
        log.info("Test findScroll");
        List<Integer> ids = List.of(getNextId(), getNextId(), getNextId());
        List<CategoryRequest> requests = List.of(new CategoryRequest("Category 1"),
                                                 new CategoryRequest("Category 2"),
                                                 new CategoryRequest("Category 3"));
        requests.forEach(categoryDao::save);
        
        ScrollRequest scrollRequest = new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD, 2, List.of("category_id", "desc"));
        ScrollResponse<CategoryResponse> result = categoryDao.findScroll(scrollRequest);
        
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(ids.get(2), "Category 3", List.of()), 
                                                           new CategoryResponse(ids.get(1), "Category 2", List.of()));

        softAssert().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softAssert().as("Size").assertThat(result.getSize()).isEqualTo(2);
        softAssert().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softAssert().as("Next").assertThat(result.isNext()).isEqualTo(true);

        softAssert().as("Content size").assertThat(result.getContent()).hasSize(2);
        softAssert().as("Content").assertThat(result.getContent()).containsExactlyElementsOf(expectedResponses);
        
        scrollRequest = new ScrollRequest(List.of(ids.get(1)), ScrollPosition.Direction.FORWARD, 2, List.of("category_id", "desc"));
        result = categoryDao.findScroll(scrollRequest);
        
        expectedResponses = List.of(new CategoryResponse(ids.getFirst(), "Category 1", List.of()));

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
        CategoryResponse expectedResponse = new CategoryResponse(getNextId(), "New", List.of());
        softAssert().as("Create new category").assertThat(categoryDao.save(request)).isEqualTo(getId());
        softAssert().as("New category").assertThat(categoryDao.findById(getId())).hasValue(expectedResponse);
    }

    @Test
    public void testUpdate() {
        log.info("Test update");
        CategoryRequest newRequest = new CategoryRequest("New");
        CategoryRequest updateRequest = new CategoryRequest("Updated");
        CategoryResponse expectedResponse = new CategoryResponse(getNextId(), "Updated", List.of());
        categoryDao.save(newRequest);
        
        softAssert().as("Update category").assertThat(categoryDao.update(getId(), updateRequest)).isEqualTo(true);
        softAssert().as("Updated category").assertThat(categoryDao.findById(getId())).hasValue(expectedResponse);
    }

    @Test
    public void testDelete() {
        log.info("Test delete");
        CategoryRequest request = new CategoryRequest("Deleting");
        categoryDao.save(request);
        
        softAssert().as("Delete category").assertThat(categoryDao.deleteById(getNextId())).isEqualTo(1);
        softAssert().as("Find deleted category empty").assertThat(categoryDao.findById(getId())).isEmpty();
    }

}
