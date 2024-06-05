package com.duskio.features.category;

import com.duskio.common.BaseTest;
import com.duskio.features.post.PostResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.duskio.common.constant.Constant.API_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest
@ContextConfiguration(classes = CategoryController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryControllerTest extends BaseTest {
    
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    
    @MockBean
    private CategoryDao categoryDao;
    
    @Test
    public void testFindById() throws Exception {
        CategoryResponse expected = new CategoryResponse(1, "New", List.of());
        Mockito.when(categoryDao.findById(1)).thenReturn(Optional.of(expected));
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories/" + 1))
                                 .andReturn().getResponse().getContentAsString();
        CategoryResponse actual = objectMapper.readValue(response, CategoryResponse.class);
        softAssert().assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindByIdWithOneToMany() throws Exception {
        CategoryResponse expected = new CategoryResponse(1, "New", List.of(new PostResponse(1, "Post", "Description", LocalDateTime.now(), List.of()),
                                                                           new PostResponse(2, "Post", "Description", LocalDateTime.now(), List.of())));
        Mockito.when(categoryDao.findByIdWithPosts(1)).thenReturn(Optional.of(expected));
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories/" + 1 + "/posts"))
                                 .andReturn().getResponse().getContentAsString();
        CategoryResponse actual = objectMapper.readValue(response, CategoryResponse.class);
        softAssert().assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindAll() throws Exception {
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(1, "Category 1", List.of()),
                                                           new CategoryResponse(2, "Category 2", List.of()),
                                                           new CategoryResponse(3, "Category 3", List.of()));
        Mockito.when(categoryDao.findAll()).thenReturn(expectedResponses);
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories"))
                                 .andReturn().getResponse().getContentAsString();
        List<CategoryResponse> results = objectMapper.readValue(response, new TypeReference<>() {});
        softAssert().assertThat(results).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    public void testFindPage() throws Exception {
        PageRequest request = PageRequest.of(0, 2, Sort.Direction.DESC, "category_id");

        PagedModel<CategoryResponse> expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryResponse(3, "Category 3", List.of()),
                                                                                        new CategoryResponse(2, "Category 2", List.of())), 
                                                                                request.previousOrFirst(), 2));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories/page")
                                                                .param("page", "0")
                                                                .param("size", "2")
                                                                .param("sort", "category_id,desc"))
               .andExpect(status().isOk());

        request = PageRequest.of(1, 2, Sort.Direction.DESC, "category_id");
        expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryResponse(1, "Category 1", List.of())),
                                                   request.previousOrFirst(), 1));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories/page")
                                              .param("page", "0")
                                              .param("size", "2")
                                              .param("sort", "category_id,desc"))
               .andExpect(status().isOk());
    }

    @Test
    public void testFindScroll() {
        
    }

    @Test
    public void testSave() throws Exception {
        CategoryRequest request = new CategoryRequest("New");
        Mockito.when(categoryDao.save(request)).thenReturn(1);
        String response = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH + "categories")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        softAssert().assertThat(objectMapper.readValue(response, Integer.class)).isEqualTo(1);
    }

    @Test
    public void testUpdate() throws Exception {
        CategoryRequest request = new CategoryRequest("Updated");
        Mockito.when(categoryDao.update(1, request)).thenReturn(true);
        String response = mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "categories/" + 1)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        softAssert().assertThat(objectMapper.readValue(response, Boolean.class)).isEqualTo(true);
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.when(categoryDao.deleteById(1)).thenReturn(1);
        String response = mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "categories/" + 1))
                                 .andReturn().getResponse().getContentAsString();
        softAssert().assertThat(objectMapper.readValue(response, Integer.class)).isEqualTo(1);
    }
    
}
