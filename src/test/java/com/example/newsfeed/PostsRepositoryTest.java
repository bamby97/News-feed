package com.example.newsfeed;

import com.example.newsfeed.models.Post;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.PostsRepository;
import com.example.newsfeed.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@DataJpaTest
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void savePost_expectPost(){
        User postUSer=entityManager.persistAndFlush(new User(
                "tom"
        ));
        Post savedPost=postsRepository.save(new Post(
                123L,
                "In the park",
                "Today I went to the park",
                new Date(1997,12,11),
                postUSer
        ));

        then(postsRepository.findById(savedPost.getId()).get()).isEqualTo(savedPost);
    }

    @Test
    @Transactional
    public void getNewsFeed_expectLast10NewsFromFollowees(){
        User user1=entityManager.persistAndFlush(new User(
                "tom"
        ));
        User user2=entityManager.persistAndFlush(new User(
                "josh"
        ));
        List<Post> supposedFeed=new ArrayList<>();
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola2","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola3","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola4","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola5","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola6","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola7","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola8","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola9","como esta", Date.from(Instant.now()),user1)));
        supposedFeed.add(entityManager.persistAndFlush(new Post("hola10","como esta", Date.from(Instant.now()),user1)));
        entityManager.persistAndFlush(new Post("hola11","como esta", Date.from(Instant.now()),user1));
        entityManager.persistAndFlush(new Post("hola12","como esta", Date.from(Instant.now()),user1));

        User savedUser1= userRepository.findById(user1.getId()).get();
        User savedUser2=userRepository.findById(user2.getId()).get();
        savedUser1.getFollowers().add(savedUser2);
        Pageable sortedByDateDesc =
                PageRequest.of(0, 10, Sort.by("date").ascending());
        List<Post> feed=postsRepository.getNewsFeedFromUser(savedUser2.getId(),sortedByDateDesc).get();

        then(feed).containsAll(supposedFeed);

    }
}
