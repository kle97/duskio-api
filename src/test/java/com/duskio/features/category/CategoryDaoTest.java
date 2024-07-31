package com.duskio.features.category;

import com.duskio.common.BaseDaoTest;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
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
        categoryDao.save(new CategorySaveDto("New"));
        CategoryDto expectedResponse = new CategoryDto(1, "New");
        softly().as("findById").assertThat(categoryDao.findById(1)).hasValue(expectedResponse);
    }

    @Test
    public void testFindByIdInvalid() {
        softly().as("findById invalid").assertThat(categoryDao.findById(1)).isEmpty();
    }

    @Test
    public void testFindAll() {
        List.of(new CategorySaveDto("Category 1"), new CategorySaveDto("Category 2"), new CategorySaveDto("Category 3"))
            .forEach(categoryDao::save);
        
        softly().as("findAll")
                .assertThat(categoryDao.findAll())
                .hasSize(3)
                .containsExactly(new CategoryDto(1, "Category 1"),
                                     new CategoryDto(2, "Category 2"),
                                     new CategoryDto(3, "Category 3"));
    }

    @Test
    public void testFindPage() {
        List.of(new CategorySaveDto("Category 1"), new CategorySaveDto("Category 2"), new CategorySaveDto("Category 3"))
            .forEach(categoryDao::save);
        
        PagedModel<CategoryDto> result = categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "category_id"));
        softly().as("PageMetadata")
                .assertThat(result.getMetadata())
                .isEqualTo(new PagedModel.PageMetadata(2, 0, 3, 2));
        
        softly().as("Content size")
                .assertThat(result.getContent())
                .hasSize(2)
                .containsExactly(new CategoryDto(3, "Category 3"), new CategoryDto(2, "Category 2"));

        result = categoryDao.findPage(PageRequest.of(1, 2, Sort.Direction.DESC, "category_id"));
        softly().as("PageMetadata")
                .assertThat(result.getMetadata())
                .isEqualTo(new PagedModel.PageMetadata(2, 1, 3, 2));
        
        softly().as("Content size")
                .assertThat(result.getContent())
                .hasSize(1)
                .containsExactly(new CategoryDto(1, "Category 1"));

        result = categoryDao.findPage(PageRequest.of(2, 2, Sort.Direction.DESC, "category_id"));
        softly().as("PageMetadata")
                .assertThat(result.getMetadata())
                .isEqualTo(new PagedModel.PageMetadata(2, 2, 3, 2));
        softly().as("Content size").assertThat(result.getContent()).hasSize(0);
    }

    @Test
    public void testFindPageInvalid() {
        List.of(new CategorySaveDto("Category 1"), new CategorySaveDto("Category 2"), new CategorySaveDto("Category 3"))
            .forEach(categoryDao::save);
        softly().assertThatThrownBy(() -> categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "invalid")));
    }

    @Test
    public void testFindScroll() {
        List.of(new CategorySaveDto("Category 1"), new CategorySaveDto("Category 2"), new CategorySaveDto("Category 3"))
            .forEach(categoryDao::save);
        
        ScrollResponse<CategoryDto> result = categoryDao.findScroll(new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD,
                                                                                      2, List.of("category_id", "desc")));
        softly().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softly().as("Size").assertThat(result.getSize()).isEqualTo(2);
        softly().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softly().as("Next").assertThat(result.isNext()).isEqualTo(true);

        softly().as("Content size")
                .assertThat(result.getContent())
                .hasSize(2)
                .containsExactly(new CategoryDto(3, "Category 3"), 
                                     new CategoryDto(2, "Category 2"));
        
        result = categoryDao.findScroll(new ScrollRequest(List.of(2), ScrollPosition.Direction.FORWARD, 
                                                          2, List.of("category_id", "desc")));
        softly().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softly().as("Size").assertThat(result.getSize()).isEqualTo(1);
        softly().as("Empty").assertThat(result.isEmpty()).isEqualTo(false);
        softly().as("Next").assertThat(result.isNext()).isEqualTo(false);
        softly().as("Content size")
                .assertThat(result.getContent())
                .hasSize(1)
                .containsExactly(new CategoryDto(1, "Category 1"));

        result = categoryDao.findScroll(new ScrollRequest(List.of(1), ScrollPosition.Direction.FORWARD,
                                                          2, List.of("category_id", "desc")));
        softly().as("Limit").assertThat(result.getLimit()).isEqualTo(2);
        softly().as("Size").assertThat(result.getSize()).isEqualTo(0);
        softly().as("Empty").assertThat(result.isEmpty()).isEqualTo(true);
        softly().as("Next").assertThat(result.isNext()).isEqualTo(false);
        softly().as("Content size").assertThat(result.getContent()).hasSize(0);
    }

    @Test
    public void testFindScrollInvalid() {
        softly().assertThatThrownBy(() -> categoryDao.findScroll(new ScrollRequest(List.of(),
                                                                                   ScrollPosition.Direction.FORWARD,
                                                                                   2, List.of("invalid", "desc"))));
    }

    @Test
    public void testSave() {
        softly().as("Save").assertThat(categoryDao.save(new CategorySaveDto("New"))).isEqualTo(1);
        softly().as("Save").assertThat(categoryDao.findById(1)).hasValue(new CategoryDto(1, "New"));
    }

    @Test
    public void testSaveInvalid() {
        softly().as("Save invalid")
                .assertThatThrownBy(() -> categoryDao.save(new CategorySaveDto("")))
                .hasMessageContaining("NULL not allowed");
        softly().as("Save invalid")
                .assertThatThrownBy(() -> categoryDao.save(new CategorySaveDto("a".repeat(256))))
                .hasMessageContaining("Value too long for column");
    }

    @Test
    public void testUpdate() {
        categoryDao.save(new CategorySaveDto("New"));
        softly().as("Update").assertThat(categoryDao.update(1, new CategoryDto(1, "Updated"))).isEqualTo(true);
        softly().as("Updated").assertThat(categoryDao.findById(1)).hasValue(new CategoryDto(1, "Updated"));
    }

    @Test
    public void testUpdateInvalid() {
        softly().as("Update invalid").assertThat(categoryDao.update(2, new CategoryDto(2, ""))).isEqualTo(false);
    }

    @Test
    public void testDelete() {
        categoryDao.save(new CategorySaveDto("Deleting"));
        softly().as("Delete").assertThat(categoryDao.deleteById(1)).isEqualTo(1);
        softly().as("Delete").assertThat(categoryDao.findById(1)).isEmpty();
    }

    @Test
    public void testDeleteInvalid() {
        softly().as("Delete invalid").assertThat(categoryDao.deleteById(1)).isEqualTo(0);
    }

}
