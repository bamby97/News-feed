package com.example.newsfeed;

import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Long USER_ID = 123L;
    private static final String USER_NAME = "MIKE";

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserService userService;

    @Mock
    User userMock;

    @Before
    public void setUp(){
        when(userMock.getId()).thenReturn(USER_ID);
        when(userMock.getName()).thenReturn(USER_NAME);
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(userMock));
    }

    @Test
    public void UserFollowUser(){
        then(userService.followUser(USER_ID,USER_ID)).isEqualTo(true);
    }
}
