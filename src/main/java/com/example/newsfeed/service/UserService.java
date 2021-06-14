package com.example.newsfeed.service;

import com.example.newsfeed.exceptions.NoUserFoundException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean followUser(Long fromUid,Long toUid){
        User userFrom=userRepository.findById(fromUid).orElseThrow(()->new NoUserFoundException("User with id: "+fromUid+"does not exist"));
        User userTo=userRepository.findById(toUid).orElseThrow(()->new NoUserFoundException("User with id: "+toUid+"does not exist"));
        userTo.getFollowers().add(userFrom);
        return true;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}
