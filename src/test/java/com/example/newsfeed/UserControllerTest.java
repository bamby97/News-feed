package com.example.newsfeed;

import com.example.newsfeed.controllers.UserController;
import com.example.newsfeed.models.User;
import com.example.newsfeed.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;


    @Test
    public void createUserTest() throws Exception {
        given(userService.createUser(any(User.class))).willReturn(
                new  User("josh")
        );
        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(new User("josh"))).contentType("application/json")).andExpect(status().isCreated());
    }
    @Test
    public void followUserTest() throws Exception {
        mockMvc.perform(get("/api/user/follow?from=123&to=134")).andExpect(status().isAccepted());
    }


}
