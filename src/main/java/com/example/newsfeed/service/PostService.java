package com.example.newsfeed.service;

import com.example.newsfeed.exceptions.NoUserFoundException;
import com.example.newsfeed.models.Post;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.PostsRepository;
import com.example.newsfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostsRepository postsRepository, UserRepository userRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long uid,Post post){
        User user=userRepository.findById(uid).orElseThrow(()->new NoUserFoundException("User with id: "+uid+" does not exist"));
        post.setDate(Date.from(Instant.now()));
        post.setUser(user);
        return postsRepository.save(post);
    }

    public List<Post> getNewsFeedFromUser(Long uid){
        User user=userRepository.findById(uid).orElseThrow(()->new NoUserFoundException("User with id: "+uid+" does not exist"));
        Pageable sortedByDateDesc =
                PageRequest.of(0, 10, Sort.by("date").ascending());
        List<Post> newsFeed=postsRepository.getNewsFeedFromUser(user.getId(),sortedByDateDesc).orElseThrow(()->new RuntimeException("Could not get news feed"));
        return newsFeed;
    }
}
