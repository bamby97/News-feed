package com.example.newsfeed.controllers;

import com.example.newsfeed.models.User;
import com.example.newsfeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/follow")
    public ResponseEntity<String> followUser(@RequestParam("from")Long from,@RequestParam("to") Long to){
        userService.followUser(from,to);
        return new ResponseEntity<>("User has been created", HttpStatus.ACCEPTED);
    }
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody @Validated User user){
        User savedUser=userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
