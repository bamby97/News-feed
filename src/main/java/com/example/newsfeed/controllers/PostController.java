package com.example.newsfeed.controllers;


import com.example.newsfeed.models.Post;
import com.example.newsfeed.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{id}")
    ResponseEntity<Post> createPost(@PathVariable("id")Long id,@RequestBody Post post){
        Post savedPost= postService.createPost(id,post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/feed")
    ResponseEntity<List<Post>> createPost(@RequestParam("id") Long uid){
        List<Post> feed=postService.getNewsFeedFromUser(uid);
        return new ResponseEntity<>(feed, HttpStatus.OK);
    }
}
