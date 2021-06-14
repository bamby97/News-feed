package com.example.newsfeed;

import com.example.newsfeed.controllers.PostController;
import com.example.newsfeed.models.Post;
import com.example.newsfeed.models.User;
import com.example.newsfeed.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void createPostTest() throws Exception {
        given(postService.createPost(anyLong(),any(Post.class))).willReturn(
                new Post("hola","como estas", Date.from(Instant.now()),new User("mike"))
        );
        String json=objectMapper.writeValueAsString(new Post("hola","como estas"));
        mockMvc.perform(post("/api/posts/2").content(json).contentType("application/json")).andExpect(status().isCreated());
    }

    @Test
    public void getNewsFeedTest() throws Exception {
        mockMvc.perform(get("/api/posts/feed?id=2")).andExpect(status().isOk());
    }

}
