package com.duskio.features.category;

import com.duskio.common.BaseDaoTest;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
public class CategoryDaoTest extends BaseDaoTest {
    
    private final CategoryDao categoryDao = getJdbi().onDemand(CategoryDao.class);

    @Test
    public void testFindById() {
        categoryDao.save(new CategoryRequest("New"));
        softly().as("FindById")
                .assertThat(categoryDao.findById(1))
                .hasValue(new CategoryResponse(1, "New"));
    }

    @Test
    public void testFindByIdInvalid() {
        softly().as("FindByIdInvalid")
                .assertThat(categoryDao.findById(1))
                .isEmpty();
    }

    @Test
    public void testFindAll() {
        List.of(new CategoryRequest("Category 1"), 
                new CategoryRequest("Category 2"), 
                new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        
        softly().as("FindAll")
                .assertThat(categoryDao.findAll())
                .hasSize(3)
                .containsExactly(new CategoryResponse(1, "Category 1"), 
                                 new CategoryResponse(2, "Category 2"), 
                                 new CategoryResponse(3, "Category 3"));
    }

    @Test
    public void testFindPage() {
        List.of(new CategoryRequest("Category 1"), 
                new CategoryRequest("Category 2"), 
                new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);

        softly().as("FindPage")
                .assertThat(categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "category_id")))
                .returns(new PagedModel.PageMetadata(2, 0, 3, 2), Assertions.from(PagedModel::getMetadata))
                .returns(List.of(new CategoryResponse(3, "Category 3"), new CategoryResponse(2, "Category 2")), 
                         Assertions.from(PagedModel::getContent));
        
        softly().as("FindPage")
                .assertThat(categoryDao.findPage(PageRequest.of(1, 2, Sort.Direction.DESC, "category_id")))
                .returns(new PagedModel.PageMetadata(2, 1, 3, 2), Assertions.from(PagedModel::getMetadata))
                .returns(List.of(new CategoryResponse(1, "Category 1")), Assertions.from(PagedModel::getContent));
        
        softly().as("FindPage")
                .assertThat(categoryDao.findPage(PageRequest.of(2, 2, Sort.Direction.DESC, "category_id")))
                .returns(new PagedModel.PageMetadata(2, 2, 3, 2), Assertions.from(PagedModel::getMetadata))
                .returns(List.of(), Assertions.from(PagedModel::getContent));
    }

    @Test
    public void testFindPageInvalid() {
        List.of(new CategoryRequest("Category 1"), 
                new CategoryRequest("Category 2"), 
                new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        softly().assertThatThrownBy(() -> categoryDao.findPage(PageRequest.of(0, 2, Sort.Direction.DESC, "invalid")))
                .hasMessageContaining("Column \"INVALID\" not found");
    }

    @Test
    public void testFindScroll() {
        List.of(new CategoryRequest("Category 1"), 
                new CategoryRequest("Category 2"), 
                new CategoryRequest("Category 3"))
            .forEach(categoryDao::save);
        
        softly().as("FindScroll")
                .assertThat(categoryDao.findScroll(new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD,
                                                                     2, List.of("category_id", "desc"))))
                .returns(2, Assertions.from(ScrollResponse::getLimit))
                .returns(2, Assertions.from(ScrollResponse::getSize))
                .returns(false, Assertions.from(ScrollResponse::isEmpty))
                .returns(true, Assertions.from(ScrollResponse::isNext))
                .returns(List.of(new CategoryResponse(3, "Category 3"), new CategoryResponse(2, "Category 2")), 
                         Assertions.from(ScrollResponse::getContent));

        softly().as("FindScroll")
                .assertThat(categoryDao.findScroll(new ScrollRequest(List.of(2), ScrollPosition.Direction.FORWARD,
                                                                     2, List.of("category_id", "desc"))))
                .returns(2, Assertions.from(ScrollResponse::getLimit))
                .returns(1, Assertions.from(ScrollResponse::getSize))
                .returns(false, Assertions.from(ScrollResponse::isEmpty))
                .returns(false, Assertions.from(ScrollResponse::isNext))
                .returns(List.of(new CategoryResponse(1, "Category 1")), Assertions.from(ScrollResponse::getContent));

        softly().as("FindScroll")
                .assertThat(categoryDao.findScroll(new ScrollRequest(List.of(1), ScrollPosition.Direction.FORWARD,
                                                                     2, List.of("category_id", "desc"))))
                .returns(2, Assertions.from(ScrollResponse::getLimit))
                .returns(0, Assertions.from(ScrollResponse::getSize))
                .returns(true, Assertions.from(ScrollResponse::isEmpty))
                .returns(false, Assertions.from(ScrollResponse::isNext))
                .returns(List.of(), Assertions.from(ScrollResponse::getContent));
    }

    @Test
    public void testFindScrollInvalid() {
        ScrollRequest scrollRequest = new ScrollRequest(List.of(), ScrollPosition.Direction.FORWARD,
                                                        2, List.of("invalid", "desc"));
        softly().assertThatThrownBy(() -> categoryDao.findScroll(scrollRequest))
                .hasMessageContaining("Column \"INVALID\" not found");
    }

    @Test
    public void testSave() {
        softly().as("Save")
                .assertThat(categoryDao.save(new CategoryRequest("New")))
                .isEqualTo(1);
        softly().as("Save result")
                .assertThat(categoryDao.findById(1))
                .hasValue(new CategoryResponse(1, "New"));
    }

    @Test
    public void testSaveInvalid() {
        softly().as("SaveInvalid")
                .assertThatThrownBy(() -> categoryDao.save(new CategoryRequest("")))
                .hasMessageContaining("NULL not allowed");
        softly().as("SaveInvalid")
                .assertThatThrownBy(() -> categoryDao.save(new CategoryRequest("a".repeat(256))))
                .hasMessageContaining("Value too long for column");
    }

    @Test
    public void testUpdate() {
        categoryDao.save(new CategoryRequest("New"));
        softly().as("Update").assertThat(categoryDao.update(1, new CategoryRequest("Updated"))).isEqualTo(true);
        softly().as("Update result").assertThat(categoryDao.findById(1)).hasValue(new CategoryResponse(1, "Updated"));
    }

    @Test
    public void testUpdateInvalid() {
        softly().as("Update invalid").assertThat(categoryDao.update(2, new CategoryRequest(""))).isEqualTo(false);
    }

    @Test
    public void testDelete() {
        categoryDao.save(new CategoryRequest("Deleting"));
        softly().as("Delete").assertThat(categoryDao.deleteById(1)).isEqualTo(1);
        softly().as("Delete").assertThat(categoryDao.findById(1)).isEmpty();
    }

    @Test
    public void testDeleteInvalid() {
        softly().as("Delete invalid").assertThat(categoryDao.deleteById(1)).isEqualTo(0);
    }

}
