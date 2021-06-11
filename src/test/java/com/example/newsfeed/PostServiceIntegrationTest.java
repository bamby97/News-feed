package com.example.newsfeed;

import com.example.newsfeed.models.Post;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.PostsRepository;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class PostServiceIntegrationTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;
    @Test
    public void createPostTest(){
        User user=userRepository.save(new User("mike"));
        Post post= new Post("hola","como esta", Date.from(Instant.now()),user);
        Post savedPost=postService.createPost(user.getId(),post);
        then(savedPost).isEqualTo(post);
    }

    @Test
    public void getNewFeedTest(){
        User user1=userRepository.save(new User("mike"));
        User user2=userRepository.save(new User("josh"));
        List<Post> supposedFeed=new ArrayList<>();
        supposedFeed.add(postsRepository.save(new Post("hola","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola2","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola3","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola4","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola5","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola6","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola7","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola8","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola9","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(postsRepository.save(new Post("hola10","como esta", Date.from(Instant.now()),user1)));
        postsRepository.save(new Post("hola11","como esta", Date.from(Instant.now()),user1));
        postsRepository.save(new Post("hola12","como esta", Date.from(Instant.now()),user1));
        user1.getFollowers().add(user2);

        List<Post> feed=postService.getNewsFeedFromUser(user2.getId());

        then(feed).containsAll(supposedFeed);
    }
}
