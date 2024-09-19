package arom_semo.server.domain.post.controller;

import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.dto.PostModifyRequestDto;
import arom_semo.server.domain.post.dto.PostResponseDto;
import arom_semo.server.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @Autowired
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void createPostTest() throws Exception{
        // given
        String userName = "testUser";
        String groupName = "testGroup";
        String postId = String.valueOf(1L);
        PostCreateRequestDto requestDto = new PostCreateRequestDto(
                "title", "content", List.of("image1.png", "image2.png"));
        when(postService.createPost(eq(userName), eq(groupName), any(PostCreateRequestDto.class))).thenReturn(postId);

        // when & then
        mockMvc.perform(post("/api/v1/posts")
                    .param("userName", userName)
                    .param("groupName", groupName)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(postId));

        verify(postService, times(1)).createPost(anyString(), anyString(), any(PostCreateRequestDto.class));
    }

    @Test
    void fetchPostTest() throws Exception{
        // given
        PostResponseDto responseDto = new PostResponseDto(1L, "Post Title", "Post Content", null, null, List.of("imageUrl"));
        when(postService.getPost(anyLong())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/posts/{postId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.title").value("Post Title"))
                .andExpect(jsonPath("$.content").value("Post Content"));

        verify(postService, times(1)).getPost(anyLong());
    }

    @Test
    void fetchAllPostsTest() throws Exception{
        // given
        PostResponseDto responseDto1 = new PostResponseDto(1L, "Post Title 1", "Post Content 1", null, null, List.of("imageUrl1"));
        PostResponseDto responseDto2 = new PostResponseDto(2L, "Post Title 2", "Post Content 2", null, null, List.of("imageUrl2"));
        when(postService.getAllPosts()).thenReturn(List.of(responseDto1, responseDto2));

        // when & then
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Post Title 1"))
                .andExpect(jsonPath("$[1].postId").value(2L))
                .andExpect(jsonPath("$[1].title").value("Post Title 2"));

        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void modifyPostTest() throws Exception{
        // given
        PostModifyRequestDto requestDto = new PostModifyRequestDto(1L, "Modified Title", "Modified Content", null, List.of("newImage"));

        // when & then
        mockMvc.perform(patch("/api/v1/posts/{postId}", 1L)
                        .param("userName", "user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(postService, times(1)).modifyPost(anyString(), any(PostModifyRequestDto.class));
    }

    @Test
    void deletePost() throws Exception{
        // when & then
        mockMvc.perform(delete("/api/v1/posts/{postId}", 1L)
                        .param("userName", "user1"))
                .andExpect(status().isOk());

        verify(postService, times(1)).deletePost(anyString(), anyLong());
    }
}