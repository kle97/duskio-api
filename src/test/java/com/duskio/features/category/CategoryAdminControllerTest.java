package com.duskio.features.category;

import com.duskio.common.BaseTest;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.common.constant.Constant;
import com.duskio.configuration.GlobalExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
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
import org.springframework.http.ProblemDetail;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@WebMvcTest
@ContextConfiguration(classes = {CategoryAdminController.class, GlobalExceptionHandler.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryAdminControllerTest extends BaseTest {
    private static final String API_PATH = Constant.ADMIN_API_PATH + "categories";
    
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    
    @MockBean
    private CategoryDao categoryDao;
    
    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindById() throws Exception {
        CategoryDto expected = new CategoryDto(1, "New");
        Mockito.when(categoryDao.findById(1)).thenReturn(Optional.of(expected));
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                                 .andReturn().getResponse().getContentAsString();
        CategoryDto actual = objectMapper.readValue(response, CategoryDto.class);
        softly().as("findById").assertThat(actual).isEqualTo(expected);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindByIdInvalid() throws Exception {
        Mockito.when(categoryDao.findById(1)).thenReturn(Optional.empty());
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                                 .andReturn().getResponse().getContentAsString();
        ProblemDetail problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Not Found");
        softly().assertThat(problem.getStatus()).isEqualTo(404);
        softly().assertThat(problem.getDetail()).isEqualTo("Resource not found!");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH + "/1"));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindAll() throws Exception {
        List<CategoryDto> expectedResponses = List.of(new CategoryDto(1, "Category 1"),
                                                      new CategoryDto(2, "Category 2"),
                                                      new CategoryDto(3, "Category 3"));
        Mockito.when(categoryDao.findAll()).thenReturn(expectedResponses);
        String response = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                                 .andReturn().getResponse().getContentAsString();
        List<CategoryDto> results = objectMapper.readValue(response, new TypeReference<>() {});
        softly().as("findAll").assertThat(results).containsExactlyElementsOf(expectedResponses);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindPage() throws Exception {
        PageRequest request = PageRequest.of(0, 2, Sort.Direction.DESC, "category_id");
        PagedModel<CategoryDto> expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryDto(3, "Category 3"),
                                                                                   new CategoryDto(2, "Category 2")),
                                                                           request, 2));
        Mockito.when(categoryDao.findPage(Mockito.any(PageRequest.class))).thenReturn(expected);
        MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/page");
        String response = mockMvc.perform(mockBuilder.param("page", "0").param("size", "2").param("sort", "category_id,desc"))
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();
        softly().as("findPage").assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));

        request = PageRequest.of(1, 2, Sort.Direction.DESC, "category_id");
        expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryDto(1, "Category 1")), request, 2));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/page");
        response = mockMvc.perform(mockBuilder.param("page", "1").param("size", "2").param("sort", "category_id,desc"))
                          .andReturn()
                          .getResponse()
                          .getContentAsString();
        softly().as("findPage").assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));

        request = PageRequest.of(2, 2, Sort.Direction.DESC, "category_id");
        expected = new PagedModel<>(new PageImpl<>(List.of(), request, 2));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/page");
        response = mockMvc.perform(mockBuilder.param("page", "2").param("size", "2").param("sort", "category_id,desc"))
                          .andReturn()
                          .getResponse()
                          .getContentAsString();
        softly().as("findPage").assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindPageInvalid() throws Exception {
        Mockito.when(categoryDao.findPage(Mockito.any(PageRequest.class)))
               .thenThrow(UnableToCreateStatementException.class);
        MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/page");
        String response = mockMvc.perform(mockBuilder.param("page", "0").param("size", "2").param("sort", "invalid,desc"))
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();
        ProblemDetail problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH + "/page"));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindScroll()throws Exception {
        ScrollResponse<CategoryDto> expected = new ScrollResponse<>(List.of(new CategoryDto(3, "Category 3"),
                                                                            new CategoryDto(2, "Category 2")),
                                                                    2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/scroll");
        String response = mockMvc.perform(mockBuilder.param("direction", "FORWARD").param("limit", "2").param("sort", "category_id,desc"))
                                 .andReturn().getResponse().getContentAsString();
        softly().assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));

        expected = new ScrollResponse<>(List.of(new CategoryDto(1, "Category 1")), 2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/scroll");
        response = mockMvc.perform(mockBuilder.param("cursor", "2").param("direction", "FORWARD")
                                              .param("limit", "2").param("sort", "category_id,desc"))
                          .andReturn().getResponse().getContentAsString();
        softly().as("findScroll").assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));

        expected = new ScrollResponse<>(List.of(), 2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/scroll");
        response = mockMvc.perform(mockBuilder.param("cursor", "1").param("direction", "FORWARD")
                                              .param("limit", "2").param("sort", "category_id,desc"))
                          .andReturn().getResponse().getContentAsString();
        softly().as("findScroll").assertThat(response).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindScrollInvalid()throws Exception {
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class)))
               .thenThrow(UnableToCreateStatementException.class);
        MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.get(API_PATH + "/scroll");
        String response = mockMvc.perform(mockBuilder.param("direction", "FORWARD").param("limit", "2").param("sort", "invalid,desc"))
                                 .andReturn().getResponse().getContentAsString();
        ProblemDetail problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH + "/scroll"));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testSave() throws Exception {
        CategorySaveDto request = new CategorySaveDto("New");
        Mockito.when(categoryDao.save(request)).thenReturn(1);
        String response = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        softly().as("Save").assertThat(objectMapper.readValue(response, Integer.class)).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testSaveInvalid() throws Exception {
        CategorySaveDto request = new CategorySaveDto("");
        String response = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        ProblemDetail problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH));

        request = new CategorySaveDto("a".repeat(256));
        response = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                                                         .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .content(objectMapper.writeValueAsString(request)))
                          .andReturn().getResponse().getContentAsString();
        problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testUpdate() throws Exception {
        CategoryDto request = new CategoryDto(1, "Updated");
        Mockito.when(categoryDao.update(1, request)).thenReturn(true);
        String response = mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/1")
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        softly().as("Update").assertThat(objectMapper.readValue(response, Boolean.class)).isEqualTo(true);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testUpdateInvalid() throws Exception {
        CategorySaveDto request = new CategorySaveDto("");
        String response = mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/1")
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(objectMapper.writeValueAsString(request)))
                                 .andReturn().getResponse().getContentAsString();
        ProblemDetail problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH + "/1"));

        request = new CategorySaveDto("a".repeat(256));
        response = mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/1")
                                                         .with(SecurityMockMvcRequestPostProcessors.csrf())
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .content(objectMapper.writeValueAsString(request)))
                          .andReturn().getResponse().getContentAsString();
        problem = objectMapper.readValue(response, ProblemDetail.class);
        softly().assertThat(problem.getType()).isEqualTo(new URI("about:blank"));
        softly().assertThat(problem.getTitle()).isEqualTo("Bad Request");
        softly().assertThat(problem.getStatus()).isEqualTo(400);
        softly().assertThat(problem.getDetail()).isEqualTo("Invalid request content.");
        softly().assertThat(problem.getInstance()).isEqualTo(new URI(API_PATH + "/1"));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testDelete() throws Exception {
        Mockito.when(categoryDao.deleteById(1)).thenReturn(1);
        String response = mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/1")
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                                 .andReturn().getResponse().getContentAsString();
        softly().as("Delete").assertThat(objectMapper.readValue(response, Integer.class)).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testDeleteInvalid() throws Exception {
        Mockito.when(categoryDao.deleteById(1)).thenReturn(0);
        String response = mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/1")
                                                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                                 .andReturn().getResponse().getContentAsString();
        softly().as("Delete invalid").assertThat(objectMapper.readValue(response, Integer.class)).isEqualTo(0);
    }
}
