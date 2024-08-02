package com.duskio.features.category;

import com.duskio.common.BaseSpringTest;
import com.duskio.common.ScrollRequest;
import com.duskio.common.ScrollResponse;
import com.duskio.common.constant.Constant;
import com.duskio.configuration.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@WebMvcTest
@ContextConfiguration(classes = {CategoryAdminController.class, GlobalExceptionHandler.class})
public class CategoryAdminControllerTest extends BaseSpringTest {
    private static final String API_PATH = Constant.ADMIN_API_PATH + "categories";
    
    @Autowired
    private MockMvcTester mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private CategoryDao categoryDao;
    
    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindById() {
        CategoryResponse expected = new CategoryResponse(1, "New");
        Mockito.when(categoryDao.findById(1)).thenReturn(Optional.of(expected));
        softly().as("FindById response")
                .assertThat(mockMvc.get().uri(API_PATH + "/1"), CategoryResponse.class)
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindByIdInvalid() throws Exception {
        Mockito.when(categoryDao.findById(1)).thenReturn(Optional.empty());
        softly().as("FindByIdInvalid problem detail")
                .assertThat(mockMvc.get().uri(API_PATH + "/1"), ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Not Found", Assertions.from(ProblemDetail::getTitle))
                .returns(404, Assertions.from(ProblemDetail::getStatus))
                .returns("Resource not found!", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH + "/1"), Assertions.from(ProblemDetail::getInstance));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindAll() {
        List<CategoryResponse> expectedResponses = List.of(new CategoryResponse(1, "Category 1"),
                                                           new CategoryResponse(2, "Category 2"),
                                                           new CategoryResponse(3, "Category 3"));
        Mockito.when(categoryDao.findAll()).thenReturn(expectedResponses);
        softly().as("FindAll response")
                .assertThatList(mockMvc.get().uri(API_PATH), CategoryResponse.class)
                .containsExactlyElementsOf(expectedResponses);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindPage() throws Exception {
        PageRequest request = PageRequest.of(0, 2, Sort.Direction.DESC, "category_id");
        PagedModel<CategoryResponse> expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryResponse(3, "Category 3"),
                                                                                        new CategoryResponse(2, "Category 2")),
                                                                                request, 2));
        Mockito.when(categoryDao.findPage(Mockito.any(PageRequest.class))).thenReturn(expected);
        softly().as("FindPage response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/page").param("page", "0")
                                         .param("size", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));

        request = PageRequest.of(1, 2, Sort.Direction.DESC, "category_id");
        expected = new PagedModel<>(new PageImpl<>(List.of(new CategoryResponse(1, "Category 1")), request, 2));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        softly().as("FindPage response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/page").param("page", "1")
                                         .param("size", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));

        request = PageRequest.of(2, 2, Sort.Direction.DESC, "category_id");
        expected = new PagedModel<>(new PageImpl<>(List.of(), request, 2));
        Mockito.when(categoryDao.findPage(request)).thenReturn(expected);
        softly().as("FindPage response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/page").param("page", "2")
                                         .param("size", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindPageInvalid() throws Exception {
        Mockito.when(categoryDao.findPage(Mockito.any(PageRequest.class)))
               .thenThrow(UnableToCreateStatementException.class);

        softly().as("FindPageInvalid problem detail")
                .assertThat(mockMvc.get().uri(API_PATH + "/page").param("page", "0")
                                   .param("size", "2").param("sort", "invalid,desc"), 
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH + "/page"), Assertions.from(ProblemDetail::getInstance));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindScroll()throws Exception {
        ScrollResponse<CategoryResponse> expected = new ScrollResponse<>(List.of(new CategoryResponse(3, "Category 3"),
                                                                                 new CategoryResponse(2, "Category 2")), 2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        softly().as("FindScroll response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/scroll").param("direction", "FORWARD")
                                         .param("limit", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));

        expected = new ScrollResponse<>(List.of(new CategoryResponse(1, "Category 1")), 2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        softly().as("FindScroll response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/scroll").param("cursor", "2").param("direction", "FORWARD")
                                         .param("limit", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));
        
        expected = new ScrollResponse<>(List.of(), 2);
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class))).thenReturn(expected);
        softly().as("FindScroll response")
                .assertThatString(mockMvc.get().uri(API_PATH + "/scroll").param("cursor", "1").param("direction", "FORWARD")
                                         .param("limit", "2").param("sort", "category_id,desc"))
                .isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testFindScrollInvalid()throws Exception {
        Mockito.when(categoryDao.findScroll(Mockito.any(ScrollRequest.class)))
               .thenThrow(UnableToCreateStatementException.class);
        softly().as("FindScrollInvalid problem detail")
                .assertThat(mockMvc.get().uri(API_PATH + "/scroll").param("direction", "FORWARD")
                                   .param("limit", "2").param("sort", "invalid,desc"),
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH + "/scroll"), Assertions.from(ProblemDetail::getInstance));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testSave() throws Exception {
        CategoryRequest request = new CategoryRequest("New");
        Mockito.when(categoryDao.save(request)).thenReturn(1);
        softly().as("Save response")
                .assertThat(mockMvc.post().uri(API_PATH)
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(request)), 
                            Integer.class)
                .isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testSaveInvalid() throws Exception {
        softly().as("SaveInvalid problem detail")
                .assertThat(mockMvc.post().uri(API_PATH)
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(new CategoryRequest(""))),
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH), Assertions.from(ProblemDetail::getInstance));

        softly().as("SaveInvalid problem detail")
                .assertThat(mockMvc.post().uri(API_PATH)
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(new CategoryRequest("a".repeat(256)))),
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH), Assertions.from(ProblemDetail::getInstance));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testUpdate() throws Exception {
        CategoryRequest request = new CategoryRequest("Updated");
        Mockito.when(categoryDao.update(1, request)).thenReturn(true);
        softly().as("Update response")
                .assertThat(mockMvc.put().uri(API_PATH + "/1")
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(request)),
                            Boolean.class)
                .isEqualTo(true);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testUpdateInvalid() throws Exception {
        softly().as("UpdateInvalid problem detail")
                .assertThat(mockMvc.put().uri(API_PATH + "/1")
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(new CategoryRequest(""))),
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH + "/1"), Assertions.from(ProblemDetail::getInstance));

        softly().as("UpdateInvalid problem detail")
                .assertThat(mockMvc.put().uri(API_PATH + "/1")
                                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(new CategoryRequest("a".repeat(256)))),
                            ProblemDetail.class)
                .returns(new URI("about:blank"), Assertions.from(ProblemDetail::getType))
                .returns("Bad Request", Assertions.from(ProblemDetail::getTitle))
                .returns(400, Assertions.from(ProblemDetail::getStatus))
                .returns("Invalid request content.", Assertions.from(ProblemDetail::getDetail))
                .returns(new URI(API_PATH + "/1"), Assertions.from(ProblemDetail::getInstance));
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testDelete() {
        Mockito.when(categoryDao.deleteById(1)).thenReturn(1);
        softly().as("Delete response")
                .assertThat(mockMvc.delete().uri(API_PATH + "/1").with(SecurityMockMvcRequestPostProcessors.csrf()),
                            Integer.class)
                .isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = Constant.ROLE_ADMIN)
    public void testDeleteInvalid() {
        Mockito.when(categoryDao.deleteById(1)).thenReturn(0);
        softly().as("DeleteInvalid response")
                .assertThat(mockMvc.delete().uri(API_PATH + "/1").with(SecurityMockMvcRequestPostProcessors.csrf()),
                            Integer.class)
                .isEqualTo(0);
    }
}
