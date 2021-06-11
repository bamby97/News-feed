package com.example.newsfeed;

import com.example.newsfeed.models.Post;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.PostsRepository;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    User user = new User(1L,"Mika");
    User user2 = new User(1L,"Mike");
    Post post=new Post(12L,"hola","como esta",Date.from(Instant.now()),user);
    @Mock
    UserRepository userRepository;
    @Mock
    PostsRepository postsRepository;

    @InjectMocks
    PostService postService;
    @Mock
    Post postMock;
    @Mock
    User userMock;

    @Before
    public void setUp(){
        when(postMock.getId()).thenReturn(post.getId());
        when(userMock.getId()).thenReturn(user.getId());
        Pageable sortedByDateDesc =
                PageRequest.of(0, 10, Sort.by("date").ascending());
        when(postsRepository.getNewsFeedFromUser(user2.getId(),sortedByDateDesc)).thenReturn(Optional.of(Arrays.asList(postMock)));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(userMock));
        when(postsRepository.save(post)).thenReturn(postMock);
    }

    @Test
    public void createPost_expectPost(){

        //invoke and verify lookupRatingById
        then(postsRepository.save(post)).isEqualTo(postMock);

    }

    @Test
    public void getNewsFeed_expectFeed(){
        Pageable sortedByDateDesc =
                PageRequest.of(0, 10, Sort.by("date").ascending());
        then(postsRepository.getNewsFeedFromUser(user2.getId(),sortedByDateDesc).get().get(0)).isEqualTo(postMock);

    }


}
