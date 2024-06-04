package com.duskio.features.category;

import com.duskio.common.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.duskio.common.constant.Constant.API_PATH;

@Slf4j
@WebMvcTest(controllers = CategoryController.class)
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
        CategoryResponse actual = objectMapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "categories/" + 1))
                                                                .andReturn()
                                                                .getResponse()
                                                                .getContentAsString(), 
                                                         CategoryResponse.class);
        log.info("Verify find category by id {}", 1);
        softAssert().as("Category").assertThat(actual).isEqualTo(expected);;
        
    }
    
}
